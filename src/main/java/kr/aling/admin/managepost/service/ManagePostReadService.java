package kr.aling.admin.managepost.service;

import kr.aling.admin.managepost.dto.response.ReadManagePostResponseDto;
import kr.aling.admin.managepost.dto.response.ReadManagePostsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 관리게시글 조회 Service interface.
 *
 * @author : 이수정
 * @since : 1.0
 */
public interface ManagePostReadService {

    /**
     * 관리게시글을 페이징 조회합니다.
     *
     * @param type 관리게시글 타입
     * @param pageable 페이징 정보를 담은 객체
     * @return 페이징 조회된 관리게시글
     * @author : 이수정
     * @since : 1.0
     */
    Page<ReadManagePostsResponseDto> getManagePosts(String type, Pageable pageable);

    /**
     * 번호에 해당하는 관리게시글을 상세 조회합니다.
     *
     * @param no 조회할 관리게시글의 번호
     * @return 상세 조회한 관리게시글
     * @author : 이수정
     * @since : 1.0
     */
    ReadManagePostResponseDto getManagePost(long no);
}
