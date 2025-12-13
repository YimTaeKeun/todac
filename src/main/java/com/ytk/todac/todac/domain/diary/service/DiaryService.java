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
        UserDTO dto = userService.getAuthenticatedUser(); // 로그인한 사용자를 가져옵니다.
        return userRepository.findById(dto.getId()).orElseThrow(
                () -> new CustomException(Level.ERROR, "로그인된 사용자를 DB에서 찾을 수 없음.")
        );
    }
}
