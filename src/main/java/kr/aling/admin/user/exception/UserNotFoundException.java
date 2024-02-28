package kr.aling.admin.user.exception;

/**
 * 회원이 존재하지 않을 때 발생하는 Exception.
 *
 * @author 이수정
 * @since 1.0
 */
public class UserNotFoundException extends RuntimeException {

    public static final String MESSAGE = "User not found! : ";

    public UserNotFoundException(long userNo) {
        super(MESSAGE + userNo);
    }
}
