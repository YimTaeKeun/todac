package com.ytk.todac.todac.domain.diary.controller;

import com.ytk.todac.todac.domain.diary.dto.DiaryDTO;
import com.ytk.todac.todac.domain.diary.dto.DiaryResponseDTO;
import com.ytk.todac.todac.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 다이어리와 관련된 작업에 대한 명세를 해당 클래스에서 진행합니다.
 * */
@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {
    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<DiaryResponseDTO> uploadDiary(@RequestBody DiaryDTO diaryDTO){
        DiaryResponseDTO response = diaryService.save(diaryDTO); // 다이어리를 저장합니다.
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
