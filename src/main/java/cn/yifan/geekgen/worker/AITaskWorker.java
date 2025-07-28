package cn.yifan.geekgen.worker;

import cn.yifan.geekgen.constant.ChatMessageRole;
import cn.yifan.geekgen.mapper.ChatMessageMapper;
import cn.yifan.geekgen.pojo.entity.ChatMessage;
import cn.yifan.geekgen.service.base.StreamAiService;
import cn.yifan.geekgen.constant.ChatTaskStatus;
import cn.yifan.geekgen.constant.RedisConstants;
import cn.yifan.geekgen.mapper.ChatTaskMapper;
import cn.yifan.geekgen.pojo.entity.ChatTask;
import cn.yifan.geekgen.pojo.rabbit.TaskMessage;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.service.TokenStream;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.api.stream.StreamAddArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @FileName AITaskWorker
 * @Description
 * @Author yifan
 * @date 2025-02-26 13:17
 **/

@Service
@Slf4j
public class AITaskWorker {

    @Autowired
    private StreamAiService agentService;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private ChatTaskMapper chatTaskMapper;
    @Autowired
    private ChatMessageMapper chatMessageMapper;

    // 重试获取chatTask
    private ChatTask getChatTask(Long taskId, Integer retryTimes) {
        ChatTask chatTask = chatTaskMapper.getById(taskId);
        log.info("getChatTask方法获取chatTask: {}", chatTask);
        if (chatTask == null) {
            if (retryTimes > 0) {
                try {
                    // 让当前线程休眠 1 秒（1000 毫秒）
                    Thread.sleep(1000);
                    // 递归调用 getChatTask 方法，将 retryTimes 减 1
                    log.info("递归调用 getChatTask 方法，retryTimes: {}", retryTimes);
                    return getChatTask(taskId, retryTimes - 1);
                } catch (InterruptedException e) {
                    // 当线程在休眠过程中被中断时，会抛出 InterruptedException 异常
                    log.error("休眠过程中被中断, taskId: {}", taskId, e);
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return chatTask;
        }
    }

    @Transactional
    public void handleTask(TaskMessage taskMessage) {
        ChatTask chatTask = getChatTask(taskMessage.getTaskId(), 10);
        if (chatTask == null) {
            log.error("接收到AI生成任务, 但任务不存在, taskMessage: {}", taskMessage);
            return;
        }
        if (!chatTask.getStatus().equals(ChatTaskStatus.WAITING)) {
            log.error("接收到AI生成任务, 但任务状态不为WAITING, taskMessage: {}", taskMessage);
            return;
        }
        log.info("接收到AI生成任务, taskMessage: {}", taskMessage);
        // 插入用户消息
        ChatMessage userMessage = new ChatMessage(
            ChatMessageRole.USER,
            taskMessage.getQuestion(),
            0L,
            taskMessage.getUserId()
        );
        chatMessageMapper.insert(userMessage);
        // 更新任务
        ChatTask newChatTask = new ChatTask();
        newChatTask.setId(taskMessage.getTaskId());
        newChatTask.setStatus(ChatTaskStatus.RUNNING);
        newChatTask.setUserChatMessageId(userMessage.getId());
        chatTaskMapper.update(newChatTask);
        // 执行任务
        RStream<String, String> redisStream = redissonClient.getStream(
        RedisConstants.CHAT_TASK_PREFIX +taskMessage.getTaskId()
        );
        TokenStream tokenStream = agentService.chat(taskMessage.getUserId(), taskMessage.getQuestion());
        tokenStream.onNext((String token) -> {
            redisStream.add(StreamAddArgs.entries("status", "ing","data", token));
        });
        tokenStream.onComplete((Response<AiMessage> response) -> {
            log.info("AI生成任务完成, taskMessage: {}", taskMessage);
            redisStream.add(StreamAddArgs.entries("status", "end","data", ""));
            // chat_message
            ChatMessage assistantChatMessage = new ChatMessage(
                ChatMessageRole.ASSISTANT,
                response.content().text(),
                Long.valueOf(response.tokenUsage().outputTokenCount()),
                taskMessage.getUserId()
            );
            chatMessageMapper.insert(assistantChatMessage);
            userMessage.setTokens(Long.valueOf(response.tokenUsage().inputTokenCount()));
            chatMessageMapper.update(userMessage);
            // chat_task
            ChatTask updateChatTask = new ChatTask();
            updateChatTask.setId(taskMessage.getTaskId());
            updateChatTask.setStatus(ChatTaskStatus.SUCCEEDED);
            updateChatTask.setAssistantChatMessageId(assistantChatMessage.getId());
            chatTaskMapper.update(updateChatTask);
            //redisStream.delete();
        });
        tokenStream.onError((Throwable error) -> {
            log.error("AI生成任务出错, taskMessage: {}", taskMessage, error);
            redisStream.add(StreamAddArgs.entries("status", "error","data", error.getMessage()));
            ChatTask updateChatTask = new ChatTask();
            updateChatTask.setId(taskMessage.getTaskId());
            updateChatTask.setStatus(ChatTaskStatus.FAILED);
            chatTaskMapper.update(updateChatTask);
            //redisStream.delete();
        });
        tokenStream.start();
    }

}
