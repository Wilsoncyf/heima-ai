package com.itheima.ai.controller;

import com.itheima.ai.entity.dto.LearningProgressDTO;
import com.itheima.ai.entity.vo.LearningProgressVO;
import com.itheima.ai.entity.vo.Result;
import com.itheima.ai.service.ILearningProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;

@RestController
@RequestMapping("/ai/learning")
@RequiredArgsConstructor
public class LearningProgressController {
    
    private final ILearningProgressService learningProgressService;
    private final ChatClient learningChatClient;
    
    @PostMapping("/progress")
    public Result<String> updateProgress(@RequestBody LearningProgressDTO dto) {
        learningProgressService.updateProgress(dto);
        return Result.success("学习进度更新成功");
    }
    
    @GetMapping("/progress/{userId}")
    public Result<LearningProgressVO> getProgress(@PathVariable String userId) {
        LearningProgressVO progress = learningProgressService.getProgressByUser(userId);
        return Result.success(progress);
    }
    
    @GetMapping("/report/{userId}")
    public Result<String> generateReport(@PathVariable String userId) {
        String report = learningProgressService.generateLearningReport(userId);
        return Result.success(report);
    }
    
    @GetMapping("/suggestions/{userId}")
    public Result<List<String>> getSuggestions(@PathVariable String userId) {
        List<String> suggestions = learningProgressService.getStudySuggestions(userId);
        return Result.success(suggestions);
    }
    
    @RequestMapping(value = "/chat", produces = "text/html;charset=utf-8")
    public Flux<String> learningChat(
            @RequestParam("prompt") String prompt,
            @RequestParam("chatId") String chatId) {
        
        return learningChatClient.prompt()
                .user(prompt)
                .advisors(a -> a.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId))
                .stream()
                .content();
    }
}