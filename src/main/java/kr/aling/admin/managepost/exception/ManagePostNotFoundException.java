package kr.aling.admin.managepost.exception;

/**
 * 관리게시물이 존재하지 않을 때 발생하는 Exception.
 *
 * @author 이수정
 * @since 1.0
 */
public class ManagePostNotFoundException extends RuntimeException {

    public static final String MESSAGE = "ManagePost not found! : ";

    public ManagePostNotFoundException(long no) {
        super(MESSAGE + no);
    }
}
