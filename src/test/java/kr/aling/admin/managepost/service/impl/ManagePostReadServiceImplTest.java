package kr.aling.admin.managepost.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import kr.aling.admin.common.dto.PageResponseDto;
import kr.aling.admin.managepost.dto.response.ReadManagePostResponseDto;
import kr.aling.admin.managepost.dto.response.ReadManagePostsResponseDto;
import kr.aling.admin.managepost.dummy.ManagePostDummy;
import kr.aling.admin.managepost.entity.ManagePost;
import kr.aling.admin.managepost.exception.ManagePostNotFoundException;
import kr.aling.admin.managepost.repository.ManagePostReadRepository;
import kr.aling.admin.managepost.service.ManagePostReadService;
import kr.aling.admin.managepost.type.ManagePostType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class ManagePostReadServiceImplTest {

    private ManagePostReadService managePostReadService;

    private ManagePostReadRepository managePostReadRepository;

    @BeforeEach
    void setUp() {
        managePostReadRepository = mock(ManagePostReadRepository.class);

        managePostReadService = new ManagePostReadServiceImpl(
                managePostReadRepository
        );
    }

    @Test
    @DisplayName("관리게시글 페이징 조회 성공 - 모든 타입")
    void getManagePosts() {
        // given
        List<ReadManagePostsResponseDto> managePostsResponseDtos = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            managePostsResponseDtos.add(new ReadManagePostsResponseDto(
                    (long) i, ManagePostType.NOTICE.name(), "Title " + i));
        }

        Pageable pageable = PageRequest.of(0, 3);
        Page<ReadManagePostsResponseDto> page =
                new PageImpl<>(managePostsResponseDtos, pageable, managePostsResponseDtos.size());

        when(managePostReadRepository.findAllByAll(any())).thenReturn(page);

        // when
        PageResponseDto<ReadManagePostsResponseDto> responseDto = managePostReadService.getManagePosts(null, pageable);

        // then
        assertThat(responseDto.getPageNumber()).isZero();
        assertThat(responseDto.getTotalPages()).isEqualTo(2);
        assertThat(responseDto.getTotalElements()).isEqualTo(5);
        assertThat(responseDto.getContent().get(0).getUserNo()).isEqualTo(1);
        assertThat(responseDto.getContent().get(0).getType()).isEqualTo(ManagePostType.NOTICE.name());
        assertThat(responseDto.getContent().get(0).getTitle()).isEqualTo("Title 1");

        verify(managePostReadRepository, times(1)).findAllByAll(any());
        verify(managePostReadRepository, never()).findAllByType(anyString(), any());
    }

    @Test
    @DisplayName("관리게시글 페이징 조회 성공 - 타입 지정")
    void getManagePosts_byType() {
        // given
        List<ReadManagePostsResponseDto> managePostsResponseDtos = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            managePostsResponseDtos.add(new ReadManagePostsResponseDto(
                    (long) i, ManagePostType.NOTICE.name(), "Title " + i));
        }

        Pageable pageable = PageRequest.of(0, 3);
        Page<ReadManagePostsResponseDto> page =
                new PageImpl<>(managePostsResponseDtos, pageable, managePostsResponseDtos.size());

        when(managePostReadRepository.findAllByType(anyString(), any())).thenReturn(page);

        // when
        PageResponseDto<ReadManagePostsResponseDto> responseDto =
                managePostReadService.getManagePosts(ManagePostType.NOTICE.name(), pageable);

        // then
        assertThat(responseDto.getPageNumber()).isZero();
        assertThat(responseDto.getTotalPages()).isEqualTo(2);
        assertThat(responseDto.getTotalElements()).isEqualTo(5);
        assertThat(responseDto.getContent().get(0).getUserNo()).isEqualTo(1);
        assertThat(responseDto.getContent().get(0).getType()).isEqualTo(ManagePostType.NOTICE.name());
        assertThat(responseDto.getContent().get(0).getTitle()).isEqualTo("Title 1");

        verify(managePostReadRepository, never()).findAllByAll(any());
        verify(managePostReadRepository, times(1)).findAllByType(anyString(), any());
    }


    @Test
    @DisplayName("관리게시글 상세 조회 성공")
    void getManagePost() {
        // given
        long no = 1L;
        ManagePost managePost = ManagePostDummy.dummy();

        when(managePostReadRepository.existsById(anyLong())).thenReturn(true);
        when(managePostReadRepository.findDetailByNo(anyLong())).thenReturn(new ReadManagePostResponseDto(
                managePost.getUserNo(), managePost.getType(), managePost.getTitle(), managePost.getContent()
        ));

        // when
        ReadManagePostResponseDto responseDto = managePostReadService.getManagePost(no);

        // then
        assertThat(responseDto.getUserNo()).isEqualTo(1);
        assertThat(responseDto.getType()).isEqualTo(managePost.getType());
        assertThat(responseDto.getTitle()).isEqualTo(managePost.getTitle());
        assertThat(responseDto.getContent()).isEqualTo(managePost.getContent());

        verify(managePostReadRepository, times(1)).existsById(anyLong());
        verify(managePostReadRepository, times(1)).findDetailByNo(anyLong());
    }

    @Test
    @DisplayName("관리게시글 상세 조회 실패 - 존재하지 않는 번호일 때")
    void getManagePost_notExistsNo() {
        // given
        when(managePostReadRepository.existsById(anyLong())).thenReturn(false);

        // when
        assertThatThrownBy(() -> managePostReadService.getManagePost(0L)).isInstanceOf(
                ManagePostNotFoundException.class);

        // then
        verify(managePostReadRepository, times(1)).existsById(anyLong());
        verify(managePostReadRepository, never()).findDetailByNo(anyLong());
    }
}