package com.ytk.todac.todac.global.jwt.token.refresh;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByJti(String jti);
    RefreshToken findByRefreshToken(String refreshToken);
}
