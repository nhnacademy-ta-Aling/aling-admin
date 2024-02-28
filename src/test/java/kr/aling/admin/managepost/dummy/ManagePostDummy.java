package kr.aling.admin.managepost.dummy;

import kr.aling.admin.managepost.entity.ManagePost;
import kr.aling.admin.managepost.type.ManagePostType;

/**
 * 관리게시글 테스트용 Dummy class.
 *
 * @author 이수정
 * @since 1.0
 */
public class ManagePostDummy {

    public static ManagePost dummy() {
        return ManagePost.builder()
                .managePostNo(1L)
                .userNo(1L)
                .type(ManagePostType.NOTICE.name())
                .title("관리게시글 제목")
                .content("관리게시글 내용")
                .build();
    }
}
