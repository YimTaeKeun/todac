package com.ytk.todac.todac.domain.ai.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;

/**
 * Ai 응답을 아래 클래스에 맞게 매핑합니다.
 * */
@Builder
@Getter
public class AiResponseDTO {
    /**
     * "sentiment_score": number,
     *   "sentiment_label": "positive" | "neutral" | "negative" | "mixed",
     *   "comment_ko": string,
     *   "rationale": {
     *     "signals": [string],
     *     "used_evidence": [string]
     *   }
     * */
    private Float sentiment_score; // 0에서 1로 갈 수록 기분이 좋음
    private String sentiment_label;
    private String comment_ko; // 해당 일기에 대한 ai 코멘트
    private Rationale rationale;

    @Builder
    @Getter
    public static class Rationale{
        private ArrayList<String> signals = new ArrayList<>();
        private ArrayList<String> used_evidence = new ArrayList<>();
    }
}
