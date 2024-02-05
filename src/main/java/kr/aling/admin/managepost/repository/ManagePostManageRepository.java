package kr.aling.admin.managepost.repository;

import kr.aling.admin.managepost.entity.ManagePost;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 관리 게시글 CUD JpaRepository.
 *
 * @author : 이수정
 * @since : 1.0
 */
public interface ManagePostManageRepository extends JpaRepository<ManagePost, Long> {

}
