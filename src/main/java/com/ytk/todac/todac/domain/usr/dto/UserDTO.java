package com.ytk.todac.todac.domain.usr.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * 해당 클래스는 클라이언트(사용자)에게 반환하기 위해 해당 클래스를 쓰고, 서비스 간의 통신을 원활하게 하기위해 사용되기도 합니다.
 * @author ytk03
 * @since 2025-12-07
 * */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String username;
    private String registerService;
    private String socialUniqueId;
}
