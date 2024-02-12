package kr.aling.admin.managepost.service.impl;

import static org.mockito.Mockito.mock;

import kr.aling.admin.managepost.repository.ManagePostReadRepository;
import kr.aling.admin.managepost.service.ManagePostReadService;
import kr.aling.admin.managepost.type.ManagePostType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

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

        // when
        managePostReadService.getManagePosts(null, PageRequest.of(0, 3));
    }

    @Test
    @DisplayName("관리게시글 페이징 조회 성공 - 타입 지정")
    void getManagePosts_byType() {
        // given

        // when
        managePostReadService.getManagePosts(ManagePostType.NOTICE.name(), PageRequest.of(0, 3));
    }


    @Test
    @DisplayName("관리게시글 상세 조회 성공")
    void getManagePost() {

    }

    @Test
    @DisplayName("관리게시글 상세 조회 실패 - 존재하지 않는 번호일 때")
    void getManagePost_notExistsNo() {

    }
}