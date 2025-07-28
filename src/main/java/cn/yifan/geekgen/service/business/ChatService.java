package cn.yifan.geekgen.service.business;

import cn.dev33.satoken.stp.StpUtil;
import cn.yifan.geekgen.constant.ChatTaskMessageStatus;
import cn.yifan.geekgen.constant.ChatTaskStatus;
import cn.yifan.geekgen.constant.ChatTaskType;
import cn.yifan.geekgen.constant.RedisConstants;
import cn.yifan.geekgen.exception.ApiError;
import cn.yifan.geekgen.mapper.ChatTaskMapper;
import cn.yifan.geekgen.pojo.dto.CreateChatTaskDTO;
import cn.yifan.geekgen.pojo.entity.ChatTask;
import cn.yifan.geekgen.pojo.rabbit.LinkedQueue;
import cn.yifan.geekgen.pojo.rabbit.TaskMessage;
import cn.yifan.geekgen.pojo.vo.CreateChatTaskVO;
import cn.yifan.geekgen.service.base.StreamAiService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.redisson.api.RStream;
import org.redisson.api.RedissonClient;
import org.redisson.api.StreamMessageId;
import org.redisson.api.stream.StreamReadArgs;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @FileName ChatService
 * @Description
 * @Author yifan
 * @date 2025-02-26 13:51
 **/

@Service
@Slf4j
public class ChatService {

    @Autowired
    private ChatTaskMapper chatTaskMapper;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private List<LinkedQueue> rabbitQueues;

    // 随机选取队列
    public LinkedQueue getRandomQueue() {
        Random random = new Random();
        int randomIndex = random.nextInt(rabbitQueues.size());
        return rabbitQueues.get(randomIndex);
    }

    //@Transactional
    public CreateChatTaskVO createTask(CreateChatTaskDTO createChatTaskDTO) {
        Long userId = StpUtil.getLoginIdAsLong();
        ChatTask chatTask = new ChatTask(
                ChatTaskType.COMPLETION,
                createChatTaskDTO.getQuestion(), // TODO data应该给什么值？
                ChatTaskStatus.WAITING,
                userId
        );
        chatTaskMapper.insert(chatTask);

        log.info("创建chatTask: {}", chatTask);

        // 随机选取队列
        LinkedQueue queue = getRandomQueue();
        // 构造消息
        TaskMessage taskMessage = new TaskMessage(
            chatTask.getId(),
            userId,
            createChatTaskDTO.getQuestion()
        );
        // 发送到RabbitMQ
        rabbitTemplate.convertAndSend(
            queue.getExchangeName(),
            queue.getRoutingKey(),
            taskMessage
        );

        return new CreateChatTaskVO(chatTask.getId());
    }

    @Async
    public void getCompletion(SseEmitter emitter, Long taskId) {
        ChatTask chatTask = chatTaskMapper.getById(taskId);
        RStream<String, String> redisStream = redissonClient.getStream(RedisConstants.CHAT_TASK_PREFIX + taskId);
        String errorMessage = getChatTaskError(chatTask, redisStream);

        if (errorMessage != null) {
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                .data(errorMessage)
                .name("error");
            try {
                emitter.send(event);
            } catch (IOException e) {
                emitter.completeWithError(e);
                return;
            }
            emitter.complete();
            return;
        }

        StreamMessageId lastId = StreamMessageId.ALL; // 从最开始的offset读取消息
        while (true) {
            // 从上次读取的位置继续读取消息
            Map<StreamMessageId, Map<String, String>> messages = redisStream.read(StreamReadArgs.greaterThan(lastId).count(1).timeout(Duration.ofSeconds(0))); // TODO 可优化参数
            if (!messages.isEmpty()) {
                // 遍历获取Map中的每对消息
                for (Map.Entry<StreamMessageId, Map<String, String>> entry : messages.entrySet()) {
                    StreamMessageId messageId = entry.getKey();
                    Map<String, String> messageData = entry.getValue();
                    try {
                        SseEmitter.SseEventBuilder event = SseEmitter.event()
                                .data(messageData)
                                .name("message");
                        emitter.send(event);
                        String status = messageData.get("status");
                        if (status.equals(ChatTaskMessageStatus.END)) {
                            emitter.complete();
                            return;
                        }
                    } catch (IOException e) {
                        // 发生异常时，完成 SseEmitter 并设置异常信息
                        emitter.completeWithError(e);
                        return;
                    }
                    // 更新最后读取的消息 ID
                    lastId = messageId;
                }
            }
        }
    }

    @Nullable
    private static String getChatTaskError(ChatTask chatTask, RStream<String, String> redisStream) {
        String errorMessage = null;
        if (chatTask == null) {
            errorMessage = ApiError.CHAT_TASK_NOT_EXIST.getMessage();
        }
        else if (chatTask.getStatus().equals(ChatTaskStatus.WAITING)) {
            errorMessage = ApiError.CHAT_TASK_IS_WAITING.getMessage();
        }
        /*else if (chatTask.getStatus().equals(ChatTaskStatus.SUCCEEDED) || chatTask.getStatus().equals(ChatTaskStatus.FAILED)) {
            errorMessage = ApiError.CHAT_TASK_IS_FINISHED.getMessage();
        }
        else if (!redisStream.isExists()) {
            errorMessage = ApiError.CHAT_TASK_IS_FINISHED.getMessage();
        }*/
        return errorMessage;
    }

}
