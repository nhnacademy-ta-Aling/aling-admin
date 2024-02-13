package kr.aling.admin.managepost.controller;

import kr.aling.admin.common.dto.PageResponseDto;
import kr.aling.admin.managepost.dto.response.ReadManagePostResponseDto;
import kr.aling.admin.managepost.dto.response.ReadManagePostsResponseDto;
import kr.aling.admin.managepost.service.ManagePostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리게시글 조회 RestController.
 *
 * @author : 이수정
 * @since : 1.0
 */
@RequiredArgsConstructor
@RequestMapping("/api/v1/manage-posts")
@RestController
public class ManagePostReadController {

    private final ManagePostReadService managePostReadService;

    /**
     * 관리게시글을 페이징 조회합니다. 적절한 type 파라미터를 입력하면 type에 따라 분류해 조회합니다.
     *
     * @param type     관리게시물 타입
     * @param pageable 페이징 정보를 담은 객체
     * @return 페이징 조회된 관리게시글
     * @author : 이수정
     * @since : 1.0
     */
    @GetMapping
    public ResponseEntity<PageResponseDto<ReadManagePostsResponseDto>> getManagePosts(
            @RequestParam(required = false) String type, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(managePostReadService.getManagePosts(type, pageable));
    }

    /**
     * 관리게시글을 상세 조회합니다.
     *
     * @param no 조회할 관리게시글의 번호
     * @return 상세 조회한 관리게시글
     * @author : 이수정
     * @since : 1.0
     */
    @GetMapping("/{no}")
    public ResponseEntity<ReadManagePostResponseDto> getManagePost(@PathVariable long no) {
        return ResponseEntity.status(HttpStatus.OK).body(managePostReadService.getManagePost(no));
    }
}
