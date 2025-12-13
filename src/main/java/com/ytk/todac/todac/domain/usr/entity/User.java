package com.ytk.todac.todac.domain.usr.entity;

import com.ytk.todac.todac.domain.diary.entity.Diary;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, // 컬렉션 크기가 매우 작다면 EAGER 쓰는 것도 좋음
            mappedBy = "user"
    )
    private List<Diary> diaries = new ArrayList<>();
}
