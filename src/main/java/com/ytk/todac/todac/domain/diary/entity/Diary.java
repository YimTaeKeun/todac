package com.ytk.todac.todac.domain.diary.entity;

import com.ytk.todac.todac.domain.usr.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Diary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String diaryTitle;

    @CreatedDate
    private LocalDateTime createdAt;

    @Lob
    @Column(columnDefinition = "LONGTEXT") // 길이 제한 용량 최대 4gb
    private String diaryContent;

    @ManyToOne(
            fetch = FetchType.LAZY // 지연 로딩, 필요할 때만 유저 접근 but, getUser()를 너무 많이 반복한다면 EAGER로 수정해야함.
    )
    @JoinColumn(name = "user_id")
    private User user;

    // ai 관련 필드
    @Column(nullable = true)
    @Setter
    private Float aiSentiment;

    @Column(nullable = true)
    @Setter
    private String aiSentimentTag;

    @Lob
    @Column(columnDefinition = "LONGTEXT", nullable = true)
    @Setter
    private String aiComment;


    public Diary(String diaryTitle, String diaryContent, User user) {
        this.diaryTitle = diaryTitle;
        this.diaryContent = diaryContent;
        this.user = user;
    }



}
