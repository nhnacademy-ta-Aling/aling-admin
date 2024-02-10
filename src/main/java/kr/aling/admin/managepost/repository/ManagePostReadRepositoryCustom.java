package kr.aling.admin.managepost.repository;

import kr.aling.admin.managepost.dto.response.ReadManagePostResponseDto;
import kr.aling.admin.managepost.dto.response.ReadManagePostsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * QueryDsl 관리게시물 조회를 위한 interface.
 *
 * @author : 이수정
 * @since : 1.0
 */
@NoRepositoryBean
public interface ManagePostReadRepositoryCustom {

    /**
     * 관리게시물 전체 페이징 조회합니다. (삭제된 게시물은 제외합니다.)
     *
     * @param pageable 페이징 정보를 담은 객체
     * @return 페이징 조회된 전체 관리게시물
     */
    Page<ReadManagePostsResponseDto> findAllByAll(Pageable pageable);

    /**
     * 관리게시물의 타입에 따라 페이징 조회합니다. (삭제된 게시물은 제외합니다.)
     *
     * @param type 관리게시물 타입
     * @param pageable 페이징 정보를 담은 객체
     * @return 타입에 따라 페이징 조회된 관리게시물
     * @author : 이수정
     * @since : 1.0
     */
    Page<ReadManagePostsResponseDto> findAllByType(String type, Pageable pageable);

    /**
     * 관리게시글 번호에 따라 상세 조회합니다.
     *
     * @param no 관리게시물 번호
     * @return 관리게시물 상세 정보를 담은 Dto
     * @author : 이수정
     * @since : 1.0
     */
    ReadManagePostResponseDto findByNo(long no);
}
