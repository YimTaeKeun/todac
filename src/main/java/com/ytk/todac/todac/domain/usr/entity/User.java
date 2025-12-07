package com.ytk.todac.todac.domain.usr.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 서버 내의 고유 회원 번호

    @Column
    private String username; // 회원 닉네임

    @Column
    private String registerService; // 회원가입한 서비스 이름

    @Column
    private String socialUniqueId; // 소셜 로그인 서버 고유 회원 아이디
}
