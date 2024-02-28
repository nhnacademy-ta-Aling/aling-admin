package kr.aling.admin.managepost.repository;

import kr.aling.admin.managepost.entity.ManagePost;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 관리게시글 조회 JpaRepository.
 *
 * @author 이수정
 * @since 1.0
 */
public interface ManagePostReadRepository extends JpaRepository<ManagePost, Long>, ManagePostReadRepositoryCustom {

}
