package cn.yifan.geekgen.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.yifan.geekgen.pojo.dto.CreateChatTaskDTO;
import cn.yifan.geekgen.pojo.vo.CreateChatTaskVO;
import cn.yifan.geekgen.service.business.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @FileName ChatController
 * @Description
 * @Author yifan
 * @date 2025-02-26 13:50
 **/

@RestController
@RequestMapping("/chat")
@SaCheckLogin
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/tasks")
    public CreateChatTaskVO createTask(@RequestBody CreateChatTaskDTO createChatTaskDTO) {
        return chatService.createTask(createChatTaskDTO);
    }

    @GetMapping("/completion")
    @SaIgnore
    public SseEmitter getCompletion(@RequestParam("taskId") Long taskId) {
        SseEmitter emitter = new SseEmitter(0L); // 0L 表示永不过期
        chatService.getCompletion(emitter, taskId);
        return emitter;
    }

}
