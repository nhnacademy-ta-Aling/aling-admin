package kr.aling.admin.common.advice;

import kr.aling.admin.common.dto.ErrorResponseDto;
import kr.aling.admin.common.exception.CustomException;
import kr.aling.admin.managepost.exception.ManagePostAlreadyDeletedException;
import kr.aling.admin.managepost.exception.ManagePostNotFoundException;
import kr.aling.admin.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 핸들링 class.
 *
 * @author 이수정
 * @since 1.0
 */
@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    private static final String DEFAULT_HANDLE_MESSAGE = "[{}] {}";

    /**
     * Http Status 400에 해당하는 예외를 공통 처리합니다.
     *
     * @param e 400에 해당하는 예외
     * @return 400 status response
     * @author 이수정
     * @since 1.0
     */
    @ExceptionHandler({ManagePostAlreadyDeletedException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponseDto> handleBadRequestException(Exception e) {
        log.error(DEFAULT_HANDLE_MESSAGE, HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponseDto(e.getMessage()));
    }

    /**
     * Http Status 404에 해당하는 예외를 공통 처리합니다.
     *
     * @param e 404에 해당하는 예외
     * @return 404 status response
     * @author 이수정
     * @since 1.0
     */
    @ExceptionHandler({UserNotFoundException.class, ManagePostNotFoundException.class})
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(Exception e) {
        log.error(DEFAULT_HANDLE_MESSAGE, HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(e.getMessage()));
    }

    /**
     * 핸들 지정되지 않은 예외를 공통 처리합니다.
     *
     * @param e CustomException
     * @return CustomException의 Http status response
     * @author 이수정
     * @since 1.0
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDto> handleCustomException(CustomException e) {
        log.error(DEFAULT_HANDLE_MESSAGE, e.getHttpStatus(), e.getMessage());
        return ResponseEntity.status(e.getHttpStatus()).body(new ErrorResponseDto(e.getMessage()));
    }
}