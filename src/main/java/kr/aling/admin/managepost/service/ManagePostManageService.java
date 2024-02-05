package kr.aling.admin.managepost.service;

import kr.aling.admin.managepost.dto.request.CreateManagePostRequestDto;
import kr.aling.admin.managepost.dto.response.CreateManagePostResponseDto;

/**
 * 관리 게시글 CUD Service interface.
 *
 * @author : 이수정
 * @since : 1.0
 */
public interface ManagePostManageService {

    /**
     * 관리 게시글을 등록합니다.
     *
     * @param requestDto 관리 게시글 등록에 필요한 요청 파라미터를 담은 Dto
     * @return 관리 게시글 등록 후 게시글 번호 반환
     * @author : 이수정
     * @since : 1.0
     */
    CreateManagePostResponseDto registerManagePost(CreateManagePostRequestDto requestDto);
}
