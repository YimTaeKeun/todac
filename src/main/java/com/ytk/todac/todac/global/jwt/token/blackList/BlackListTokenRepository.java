package com.ytk.todac.todac.global.jwt.token.blackList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Deprecated
public interface BlackListTokenRepository extends JpaRepository<BlackListToken, Long> {
    BlackListToken findByJti(String jti);
}
