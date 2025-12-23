package com.ytk.todac.todac.global.jwt.token.blackList;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@Deprecated
public class BlackListToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    @Column(unique = true)
    private String jti;

    @CreatedDate
    private LocalDateTime blackTime;

    public BlackListToken(String token, String jti) {
        this.token = token;
        this.jti = jti;
    }
}
