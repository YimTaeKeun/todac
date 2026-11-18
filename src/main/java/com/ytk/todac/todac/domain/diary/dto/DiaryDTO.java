package com.ytk.todac.todac.domain.diary.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiaryDTO {

    @Schema(description = "다이어리의 제목을 말합니다.")
    private String title;

    @Schema(description = "다이어리 내용입니다.")
    private String content;
}
