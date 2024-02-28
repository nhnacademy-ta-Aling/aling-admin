package kr.aling.admin.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 에러 메시지 응답 Dto.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@AllArgsConstructor
public class ErrorResponseDto {

    private String message;
}
