package kr.aling.admin.managepost.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 관리게시글 생성 응답을 담는 Dto.
 *
 * @author : 이수정
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
public class CreateManagePostResponseDto {

    private final Long managePostNo;
}
