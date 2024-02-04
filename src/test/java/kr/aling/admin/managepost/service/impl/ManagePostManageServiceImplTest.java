package kr.aling.admin.managepost.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.aling.admin.managepost.dto.request.CreateManagePostRequestDto;
import kr.aling.admin.managepost.dto.response.CreateManagePostResponseDto;
import kr.aling.admin.managepost.dummy.ManagePostDummy;
import kr.aling.admin.managepost.entity.ManagePost;
import kr.aling.admin.managepost.repository.ManagePostManageRepository;
import kr.aling.admin.managepost.service.ManagePostManageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ManagePostManageServiceImplTest {

    private ManagePostManageService managePostManageService;

    private ManagePostManageRepository managePostManageRepository;

    @BeforeEach
    void setUp() {
        managePostManageRepository = mock(ManagePostManageRepository.class);

        managePostManageService = new ManagePostManageServiceImpl(
                managePostManageRepository
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

        when(managePostManageRepository.save(any())).thenReturn(managePost);

        // when
        CreateManagePostResponseDto responseDto = managePostManageService.registerManagePost(requestDto);

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getManagePostNo()).isEqualTo(managePost.getManagePostNo());

        verify(managePostManageRepository, times(1)).save(any());
    }
}