package com.ytk.todac.todac.domain.diary.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiaryResponseDTO {
    @Schema(description = "다이어리의 제목을 말합니다.")
    private String title;

    @Schema(description = "다이어리 내용입니다.")
    private String content;

    @Schema(description = "저장된 유저 정보입니다.")
    private User user;

    @AllArgsConstructor
    @Getter
    public static class User{
        private Long id;
        private String username;
    }
}
