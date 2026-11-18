package com.ytk.todac.todac.domain.usr.repository;

import com.ytk.todac.todac.domain.usr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllBySocialUniqueId(String socialUniqueId);
}
