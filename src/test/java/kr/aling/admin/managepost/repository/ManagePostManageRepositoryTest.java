package kr.aling.admin.managepost.repository;

import static org.assertj.core.api.Assertions.assertThat;

import kr.aling.admin.managepost.dummy.ManagePostDummy;
import kr.aling.admin.managepost.entity.ManagePost;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ManagePostManageRepositoryTest {

    @Autowired
    private ManagePostManageRepository managePostManageRepository;

    private ManagePost managePost;

    @BeforeEach
    void setUp() {
        managePost = ManagePostDummy.dummy();
    }

    @Test
    @DisplayName("관리게시글 등록 성공")
    void save() {
        // when
        ManagePost savedManagePost = managePostManageRepository.save(managePost);

        // then
        assertThat(savedManagePost).isNotNull();
        assertThat(savedManagePost.getType()).isEqualTo(managePost.getType());
        assertThat(savedManagePost.getTitle()).isEqualTo(managePost.getTitle());
        assertThat(savedManagePost.getContent()).isEqualTo(managePost.getContent());
        assertThat(savedManagePost.getIsDelete()).isEqualTo(Boolean.FALSE);
        assertThat(savedManagePost.getCreateAt()).isNotNull();
    }
}
