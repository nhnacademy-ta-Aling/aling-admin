package kr.aling.admin.managepost.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.aling.admin.managepost.dto.request.CreateManagePostRequestDto;
import kr.aling.admin.managepost.dto.response.CreateManagePostResponseDto;
import kr.aling.admin.managepost.dummy.ManagePostDummy;
import kr.aling.admin.managepost.entity.ManagePost;
import kr.aling.admin.managepost.repository.ManagePostManageRepository;
import kr.aling.admin.managepost.service.ManagePostManageService;
import kr.aling.admin.user.dto.response.IsExistsUserResponseDto;
import kr.aling.admin.user.exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

class ManagePostManageServiceImplTest {

    private ManagePostManageService managePostManageService;

    private ManagePostManageRepository managePostManageRepository;

    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        managePostManageRepository = mock(ManagePostManageRepository.class);
        restTemplate = mock(RestTemplate.class);

        managePostManageService = new ManagePostManageServiceImpl(
                managePostManageRepository,
                restTemplate
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

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok(isExistsUserResponse));
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

        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), any(ParameterizedTypeReference.class)))
                .thenReturn(ResponseEntity.ok(isExistsUserResponse));

        // then
        // when
        assertThatThrownBy(() -> managePostManageService.registerManagePost(requestDto)).isInstanceOf(UserNotFoundException.class);

        verify(managePostManageRepository, never()).save(any());
    }
}