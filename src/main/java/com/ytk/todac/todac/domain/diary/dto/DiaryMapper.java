package com.ytk.todac.todac.domain.diary.dto;

import com.ytk.todac.todac.domain.diary.entity.Diary;
import org.springframework.stereotype.Component;

@Component
public class DiaryMapper {

    public DiaryResponseDTO toDto(Diary diary){
        var user = diary.getUser();
        return DiaryResponseDTO.builder()
                .id(diary.getId())
                .title(diary.getDiaryTitle())
                .content(diary.getDiaryContent())
                .createdAt(diary.getCreatedAt())
                .user(new DiaryResponseDTO.User(user.getId(), user.getUsername()))
                .build();
    }
}
