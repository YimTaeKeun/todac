package com.ytk.todac.todac.domain.diary.controller;

import com.ytk.todac.todac.domain.diary.dto.DiaryDTO;
import com.ytk.todac.todac.domain.diary.dto.DiaryResponseDTO;
import com.ytk.todac.todac.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        HttpHeaders headers = new HttpHeaders();
        headers.add("Retry-After", "10"); // ai 작업 평균 소요시간
        diaryService.saveDiaryComment(response.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<DiaryResponseDTO>> getAllDiary(){
        List<DiaryResponseDTO> response = diaryService.findAllWriterDiaries();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{diary_id}")
    public ResponseEntity<?> addDiary(@PathVariable Long diary_id){
        diaryService.removeDiary(diary_id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{diary_id}")
    public ResponseEntity<DiaryResponseDTO> getDiary(@PathVariable Long diary_id){
        DiaryResponseDTO response = diaryService.findById(diary_id);
        HttpHeaders headers = new HttpHeaders();
        if(response.getAiResponse().getAiComment() == null) headers.add("Retry-After", "1");
        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .body(response);
    }
}
