package com.ytk.todac.todac.domain.diary.repository;

import com.ytk.todac.todac.domain.diary.entity.Diary;
import com.ytk.todac.todac.domain.usr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    List<Diary> findAllByUser(User user);
}
