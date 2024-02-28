package kr.aling.admin.managepost.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.aling.admin.common.feign.UserFeignClient;
import kr.aling.admin.managepost.dto.request.CreateManagePostRequestDto;
import kr.aling.admin.managepost.dto.request.ModifyManagePostRequestDto;
import kr.aling.admin.managepost.dto.response.CreateManagePostResponseDto;
import kr.aling.admin.managepost.dummy.ManagePostDummy;
import kr.aling.admin.managepost.entity.ManagePost;
import kr.aling.admin.managepost.exception.ManagePostAlreadyDeletedException;
import kr.aling.admin.managepost.exception.ManagePostNotFoundException;
import kr.aling.admin.managepost.repository.ManagePostManageRepository;
import kr.aling.admin.managepost.service.ManagePostManageService;
import kr.aling.admin.managepost.type.ManagePostType;
import kr.aling.admin.user.dto.response.IsExistsUserResponseDto;
import kr.aling.admin.user.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

class ManagePostManageServiceImplTest {

    private ManagePostManageService managePostManageService;

    private ManagePostManageRepository managePostManageRepository;

    private UserFeignClient userFeignClient;

    @BeforeEach
    void setUp() {
        managePostManageRepository = mock(ManagePostManageRepository.class);
        userFeignClient = mock(UserFeignClient.class);

        managePostManageService = new ManagePostManageServiceImpl(
                managePostManageRepository,
                userFeignClient
        );
    }

    @Test
    @DisplayName("관리게시글 등록 성공")
    void registerManagePost() {
        // given
        ManagePost managePost = ManagePostDummy.dummy();
        CreateManagePostRequestDto requestDto = new CreateManagePostRequestDto(
                managePost.getManagePostNo(), managePost.getType(), managePost.getTitle(), managePost.getContent()
        );
        IsExistsUserResponseDto isExistsUserResponse = new IsExistsUserResponseDto(Boolean.TRUE);

        when(userFeignClient.isExistsUser(anyLong())).thenReturn(ResponseEntity.ok(isExistsUserResponse));
        when(managePostManageRepository.save(any())).thenReturn(managePost);

        // when
        CreateManagePostResponseDto responseDto = managePostManageService.registerManagePost(requestDto);

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getManagePostNo()).isEqualTo(managePost.getManagePostNo());

        verify(managePostManageRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("관리게시글 등록 실패 - 회원을 찾지 못한 경우")
    void registerManagePost_userNotFound() {
        // given
        ManagePost managePost = ManagePostDummy.dummy();
        CreateManagePostRequestDto requestDto = new CreateManagePostRequestDto(
                managePost.getManagePostNo(), managePost.getType(), managePost.getTitle(), managePost.getContent()
        );
        IsExistsUserResponseDto isExistsUserResponse = new IsExistsUserResponseDto(Boolean.FALSE);

        when(userFeignClient.isExistsUser(anyLong())).thenReturn(ResponseEntity.ok(isExistsUserResponse));

        // then
        // when
        assertThatThrownBy(() -> managePostManageService.registerManagePost(requestDto)).isInstanceOf(
                UserNotFoundException.class);

        verify(managePostManageRepository, never()).save(any());
    }

    @Test
    @DisplayName("관리게시글 수정 성공")
    void modifyManagePost() {
        // given
        Long managePostNo = 1L;
        ModifyManagePostRequestDto requestDto = new ModifyManagePostRequestDto(
                ManagePostType.POLICY.name(), "title", "content"
        );
        ManagePost managePost = ManagePostDummy.dummy();
        when(managePostManageRepository.findById(anyLong())).thenReturn(Optional.of(managePost));

        // when
        managePostManageService.modifyManagePost(managePostNo, requestDto);

        // then
        assertThat(managePost.getType()).isEqualTo(ManagePostType.POLICY.name());
        assertThat(managePost.getTitle()).isEqualTo("title");
        assertThat(managePost.getContent()).isEqualTo("content");

        verify(managePostManageRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("관리게시글 수정 실패 - 관리게시글을 찾지 못한 경우")
    void modifyManagePost_managePostNotFound() {
        // given
        Long managePostNo = 1L;
        ModifyManagePostRequestDto requestDto = new ModifyManagePostRequestDto(
                ManagePostType.POLICY.name(), "title", "content"
        );
        when(managePostManageRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> managePostManageService.modifyManagePost(managePostNo, requestDto))
                .isInstanceOf(ManagePostNotFoundException.class);

        verify(managePostManageRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("관리게시글 삭제 성공")
    void deleteManagePost() {
        // given
        Long managePostNo = 1L;

        ManagePost managePost = ManagePostDummy.dummy();
        ReflectionTestUtils.setField(managePost, "isDelete", Boolean.FALSE);
        when(managePostManageRepository.findById(anyLong())).thenReturn(Optional.of(managePost));

        // when
        managePostManageService.deleteManagePost(managePostNo);

        // then
        assertThat(managePost.getIsDelete()).isEqualTo(Boolean.TRUE);

        verify(managePostManageRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("관리게시글 삭제 실패 - 관리게시글을 찾지 못한 경우")
    void deleteManagePost_managePostNotFound() {
        // given
        Long managePostNo = 1L;

        when(managePostManageRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> managePostManageService.deleteManagePost(managePostNo))
                .isInstanceOf(ManagePostNotFoundException.class);

        verify(managePostManageRepository, times(1)).findById(anyLong());
    }

    @Test
    @DisplayName("관리게시글 삭제 실패 - 관리게시글이 이미 삭제된 경우")
    void deleteManagePost_alreadyDeleted() {
        // given
        Long managePostNo = 1L;

        ManagePost managePost = ManagePostDummy.dummy();
        ReflectionTestUtils.setField(managePost, "isDelete", Boolean.TRUE);
        when(managePostManageRepository.findById(anyLong())).thenReturn(Optional.of(managePost));

        // when
        // then
        assertThatThrownBy(() -> managePostManageService.deleteManagePost(managePostNo))
                .isInstanceOf(ManagePostAlreadyDeletedException.class);

        verify(managePostManageRepository, times(1)).findById(anyLong());
    }
}