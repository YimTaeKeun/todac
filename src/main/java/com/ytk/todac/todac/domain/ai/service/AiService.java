package com.ytk.todac.todac.domain.ai.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ytk.todac.todac.domain.ai.dto.AiResponseDTO;
import com.ytk.todac.todac.domain.ai.prompt.PromptLoader;
import com.ytk.todac.todac.global.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AiService {
    private final PromptLoader promptLoader;
    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public String sayHello() {
        return chatClient.prompt()
                .user("안녕")
                .call()
                .content();
    }

    /**
     * AI 응답을 가져옵니다.
     * */
    public String getAiResponse(String systemMessage, String message){
        return chatClient.prompt()
                .system(systemMessage)
                .user(message)
                .call()
                .content();
    }

    /**
     * 일기 내용에 대한 AI 요약을 도출합니다.
     * */
    public String getDiaryAiSummary(String message){
        String system = promptLoader.load("prompts/ai_diary_summary_system.txt");
        String response = getAiResponse(system, message);
        log.debug(response);
        return response;
    }

    public AiResponseDTO getAiJudgement(String message){
        String system = promptLoader.load("prompts/ai_diary_judge_system.txt");
        String aiSummary = getDiaryAiSummary(message);
        String response = getAiResponse(system, aiSummary);
        log.debug(response);
        try{
            return objectMapper.readValue(response, AiResponseDTO.class);
        } catch (Exception e){
            throw new CustomException(Level.WARN, e.getMessage());
        }
    }




    public AiService(ChatClient.Builder chatClient, PromptLoader promptLoader, ObjectMapper objectMapper) {
        this.chatClient = chatClient.build();
        this.promptLoader = promptLoader;
        this.objectMapper = objectMapper;
    }
}
