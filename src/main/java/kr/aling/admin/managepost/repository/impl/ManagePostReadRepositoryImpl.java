package kr.aling.admin.managepost.repository.impl;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import java.util.List;
import kr.aling.admin.managepost.dto.response.ReadManagePostResponseDto;
import kr.aling.admin.managepost.dto.response.ReadManagePostsResponseDto;
import kr.aling.admin.managepost.entity.ManagePost;
import kr.aling.admin.managepost.entity.QManagePost;
import kr.aling.admin.managepost.repository.ManagePostReadRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

/**
 * QueryDsl 관리게시물 조회를 위한 repository.
 *
 * @author : 이수정
 * @since : 1.0
 */
public class ManagePostReadRepositoryImpl extends QuerydslRepositorySupport implements ManagePostReadRepositoryCustom {

    public ManagePostReadRepositoryImpl() { super(ManagePost.class); }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ReadManagePostsResponseDto> findAllByAll(Pageable pageable) {
        QManagePost managePost = QManagePost.managePost;

        List<ReadManagePostsResponseDto> managePosts = from(managePost)
                .where(managePost.isDelete.eq(Boolean.FALSE))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .select(Projections.constructor(ReadManagePostsResponseDto.class,
                        managePost.userNo,
                        managePost.type,
                        managePost.title))
                .fetch();

        JPQLQuery<Long> countQuery = from(managePost)
                .where(managePost.isDelete.eq(Boolean.FALSE))
                .select(managePost.count());

        return PageableExecutionUtils.getPage(managePosts, pageable, countQuery::fetchFirst);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<ReadManagePostsResponseDto> findAllByType(String type, Pageable pageable) {
        QManagePost managePost = QManagePost.managePost;

        List<ReadManagePostsResponseDto> managePosts = from(managePost)
                .where(managePost.isDelete.eq(Boolean.FALSE).and(managePost.type.eq(type)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .select(Projections.constructor(ReadManagePostsResponseDto.class,
                        managePost.userNo,
                        managePost.type,
                        managePost.title))
                .fetch();

        JPQLQuery<Long> countQuery = from(managePost)
                .where(managePost.isDelete.eq(Boolean.FALSE).and(managePost.type.eq(type)))
                .select(managePost.count());

        return PageableExecutionUtils.getPage(managePosts, pageable, countQuery::fetchFirst);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReadManagePostResponseDto findByNo(long no) {
        QManagePost managePost = QManagePost.managePost;

        return from(managePost)
                .where(managePost.managePostNo.eq(no))
                .select(Projections.constructor(ReadManagePostResponseDto.class,
                        managePost.userNo,
                        managePost.type,
                        managePost.title,
                        managePost.content))
                .fetchFirst();
    }
}
