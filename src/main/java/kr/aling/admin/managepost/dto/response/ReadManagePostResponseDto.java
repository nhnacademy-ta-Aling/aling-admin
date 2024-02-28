package kr.aling.admin.managepost.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 관리게시글 상세 조회 응답을 담는 Dto.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class ReadManagePostResponseDto {

    private final Long userNo;
    private final String type;
    private final String title;
    private final String content;
}
