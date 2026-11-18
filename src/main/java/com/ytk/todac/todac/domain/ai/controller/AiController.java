package com.ytk.todac.todac.domain.ai.controller;

import com.ytk.todac.todac.domain.ai.dto.AiResponseDTO;
import com.ytk.todac.todac.domain.ai.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiController {
    private final AiService aiService;
    @GetMapping("")
    public ResponseEntity<AiResponseDTO> getAiResponse(){
        AiResponseDTO aiResponseDTO = aiService.getAiJudgement("오늘 날씨가 생각보다 별로인 것 같다. 날씨도 우중충하고 3주동안 지속되는 잔기침에 정말 스트레스가 이만저만 아니다.");
        return ResponseEntity.ok(aiResponseDTO);
    }
}
