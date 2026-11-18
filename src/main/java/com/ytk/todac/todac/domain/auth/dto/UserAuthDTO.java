package com.ytk.todac.todac.domain.auth.dto;

import lombok.Getter;

/**
 * 해당 클래스는 소셜 로그인을 한 후, 서비스 레이어와 소통하기 위한 DTO 입니다.
 * 소셜 로그인을 완료한 후 아래 클래스 명세에 맞게 데이터가 매핑되어야 합니다.
 * */
@Getter
public class UserAuthDTO {
    private final String username; // 유저 이름 입니다.
    private final String socialUniqueId; // 소셜 서버에서 발급되는 유저 개인 번호 입니다.

    public UserAuthDTO(String username, String socialUniqueId) {
        this.username = username;
        this.socialUniqueId = socialUniqueId;
    }
}
