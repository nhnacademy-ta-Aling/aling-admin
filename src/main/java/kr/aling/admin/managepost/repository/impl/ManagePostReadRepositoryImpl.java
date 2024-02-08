package kr.aling.admin.managepost.repository.impl;

import kr.aling.admin.managepost.entity.ManagePost;
import kr.aling.admin.managepost.repository.ManagePostReadRepositoryCustom;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

/**
 * QueryDsl 관리게시물 조회를 위한 repository.
 *
 * @author : 이수정
 * @since : 1.0
 */
public class ManagePostReadRepositoryImpl extends QuerydslRepositorySupport implements ManagePostReadRepositoryCustom {

    public ManagePostReadRepositoryImpl() { super(ManagePost.class); }
}
