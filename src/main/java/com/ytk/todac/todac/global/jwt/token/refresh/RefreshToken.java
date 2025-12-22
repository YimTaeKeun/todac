package com.ytk.todac.todac.global.jwt.token.refresh;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String refreshToken;

    @Column(unique = true)
    private String jti;

    @Column
    @CreatedDate
    private LocalDateTime createdAt;

    public RefreshToken(String refreshToken, String jti) {
        this.refreshToken = refreshToken;
        this.jti = jti;
        this.createdAt = LocalDateTime.now();
    }
}
