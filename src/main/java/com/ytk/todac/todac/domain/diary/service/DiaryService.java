package com.ytk.todac.todac.domain.diary.service;

import com.ytk.todac.todac.domain.diary.dto.DiaryDTO;
import com.ytk.todac.todac.domain.diary.dto.DiaryMapper;
import com.ytk.todac.todac.domain.diary.dto.DiaryResponseDTO;
import com.ytk.todac.todac.domain.diary.entity.Diary;
import com.ytk.todac.todac.domain.diary.repository.DiaryRepository;
import com.ytk.todac.todac.domain.usr.dto.UserDTO;
import com.ytk.todac.todac.domain.usr.entity.User;
import com.ytk.todac.todac.domain.usr.repository.UserRepository;
import com.ytk.todac.todac.domain.usr.service.UserService;
import com.ytk.todac.todac.global.exception.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final DiaryMapper diaryMapper;

    @Transactional // 다이어리와 유저를 한꺼번에 저장하기 때문에 트랜잭션으로 묶어 처리
    public DiaryResponseDTO save(DiaryDTO diaryDTO) {
        Diary diary = diaryRepository.save(
                new Diary(
                        diaryDTO.getTitle(),
                        diaryDTO.getContent(),
                        getWriter()
                )
        );
        return diaryMapper.toDto(diary);
    }

    private User getWriter(){
        return userService.getAuthenticatedUser();
    }
}
