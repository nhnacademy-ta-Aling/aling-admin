package kr.aling.admin.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * REST-API 공통 응답 포맷 Class.
 *
 * @author : 이수정
 * @since : 1.0
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;

    private T data;
}