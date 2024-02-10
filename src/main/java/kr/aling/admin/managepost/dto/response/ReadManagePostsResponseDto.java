package kr.aling.admin.managepost.dto.response;

import lombok.AllArgsConstructor;

/**
 * 관리게시글 페이징 조회 응답을 담는 Dto.
 *
 * @author : 이수정
 * @since : 1.0
 */
@AllArgsConstructor
public class ReadManagePostsResponseDto {

    private final Long userNo;
    private final String type;
    private final String title;
}
