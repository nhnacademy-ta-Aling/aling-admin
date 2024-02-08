package kr.aling.admin.managepost.service.impl;

import java.awt.print.Pageable;
import kr.aling.admin.managepost.dto.response.ReadManagePostResponseDto;
import kr.aling.admin.managepost.dto.response.ReadManagePostsResponseDto;
import kr.aling.admin.managepost.repository.ManagePostReadRepository;
import kr.aling.admin.managepost.service.ManagePostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 관리게시글 조회 Service 구현체.
 *
 * @author : 이수정
 * @since : 1.0
 */
@RequiredArgsConstructor
@Service
public class ManagePostReadServiceImpl implements ManagePostReadService {

    private final ManagePostReadRepository managePostReadRepository;

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public Page<ReadManagePostsResponseDto> getManagePosts(String type, Pageable pageable) {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public Page<ReadManagePostResponseDto> getManagePost(long no) {
        return null;
    }
}
