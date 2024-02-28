package kr.aling.admin.managepost.exception;

/**
 * 관리게시물이 이미 삭제되었을 때 발생하는 예외.
 *
 * @author 이수정
 * @since 1.0
 */
public class ManagePostAlreadyDeletedException extends RuntimeException {

    public static final String MESSAGE = "ManagePost already deleted!";

    public ManagePostAlreadyDeletedException() {
        super(MESSAGE);
    }
}
