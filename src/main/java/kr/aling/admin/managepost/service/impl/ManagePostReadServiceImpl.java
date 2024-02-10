package kr.aling.admin.managepost.service.impl;

import kr.aling.admin.managepost.dto.response.ReadManagePostResponseDto;
import kr.aling.admin.managepost.dto.response.ReadManagePostsResponseDto;
import kr.aling.admin.managepost.repository.ManagePostReadRepository;
import kr.aling.admin.managepost.service.ManagePostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        if (type != null) {
            return managePostReadRepository.findAllByType(type, pageable);
        }
        return managePostReadRepository.findAllByAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public ReadManagePostResponseDto getManagePost(long no) {
        return managePostReadRepository.findByNo(no);
    }
}
