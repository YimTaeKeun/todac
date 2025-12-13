package com.ytk.todac.todac.domain.diary.entity;

import com.ytk.todac.todac.domain.usr.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String diaryTitle;

    @Column(length = 1000000000)
    private String diaryContent;

    @ManyToOne(
            fetch = FetchType.LAZY // 지연 로딩, 필요할 때만 유저 접근 but, getUser()를 너무 많이 반복한다면 EAGER로 수정해야함.
    )
    @JoinColumn(name = "user_id")
    private User user;

    public Diary(String diaryTitle, String diaryContent, User user) {
        this.diaryTitle = diaryTitle;
        this.diaryContent = diaryContent;
        this.user = user;
    }

}
