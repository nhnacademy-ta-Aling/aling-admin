package kr.aling.admin.common.advice;

import kr.aling.admin.common.exception.CustomException;
import kr.aling.admin.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 핸들링 class.
 *
 * @author : 이수정
 * @since : 1.0
 */
@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    /**
     * Http Status 404에 해당하는 예외를 공통 처리합니다.
     *
     * @param e 404에 해당하는 예외
     * @return 404 status response
     * @author : 이수정
     * @since : 1.0
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(Exception e) {
        log.error("[{}] {}", HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    /**
     * 핸들 지정되지 않은 예외를 공통 처리합니다.
     *
     * @param e CustomException
     * @return CustomException의 Http status response
     * @author : 이수정
     * @since : 1.0
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException e) {
        log.error("[{}] {}", e.getHttpStatus(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}