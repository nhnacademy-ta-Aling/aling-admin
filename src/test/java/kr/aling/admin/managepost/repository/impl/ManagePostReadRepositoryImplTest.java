package kr.aling.admin.managepost.repository.impl;

import static org.assertj.core.api.Assertions.assertThat;

import kr.aling.admin.managepost.dto.response.ReadManagePostResponseDto;
import kr.aling.admin.managepost.dto.response.ReadManagePostsResponseDto;
import kr.aling.admin.managepost.entity.ManagePost;
import kr.aling.admin.managepost.repository.ManagePostReadRepository;
import kr.aling.admin.managepost.type.ManagePostType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
class ManagePostReadRepositoryImplTest {

    @Autowired
    private ManagePostReadRepository managePostReadRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private ManagePost managePost;

    @BeforeEach
    void setUp() {
        managePost = ManagePost.builder()
                .userNo(1L)
                .type(ManagePostType.NOTICE.name())
                .title("Title")
                .content("Content")
                .build();
    }

    @Test
    @DisplayName("관리게시물 전체 타입 페이징 조회 성공")
    void findAllByAll() {
        // given
        Pageable pageable = PageRequest.of(0, 3);
        ManagePost persistManagePost = testEntityManager.persist(managePost);

        // when
        Page<ReadManagePostsResponseDto> page = managePostReadRepository.findAllByAll(pageable);

        // then
        assertThat(page).isNotNull();
        assertThat(page.getContent().get(0).getUserNo()).isEqualTo(persistManagePost.getUserNo());
        assertThat(page.getContent().get(0).getType()).isEqualTo(persistManagePost.getType());
        assertThat(page.getContent().get(0).getTitle()).isEqualTo(persistManagePost.getTitle());
    }

    @Test
    @DisplayName("관리게시물 특정 타입 페이징 조회 성공")
    void findAllByType() {
        // given
        Pageable pageable = PageRequest.of(0, 3);
        ManagePost persistManagePost = testEntityManager.persist(managePost);

        // when
        Page<ReadManagePostsResponseDto> page = managePostReadRepository.findAllByType(ManagePostType.NOTICE.name(), pageable);

        // then
        assertThat(page).isNotNull();
        assertThat(page.getContent().get(0).getUserNo()).isEqualTo(persistManagePost.getUserNo());
        assertThat(page.getContent().get(0).getType()).isEqualTo(persistManagePost.getType());
        assertThat(page.getContent().get(0).getTitle()).isEqualTo(persistManagePost.getTitle());
    }

    @Test
    @DisplayName("관리게시물 상세 정보 조회 성공")
    void findDetailByNo() {
        // given
        long no = 1L;
        ManagePost persistManagePost = testEntityManager.persist(managePost);

        // when
        ReadManagePostResponseDto response = managePostReadRepository.findDetailByNo(no);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getUserNo()).isEqualTo(persistManagePost.getUserNo());
        assertThat(response.getType()).isEqualTo(persistManagePost.getType());
        assertThat(response.getTitle()).isEqualTo(persistManagePost.getTitle());
        assertThat(response.getContent()).isEqualTo(persistManagePost.getContent());
    }
}