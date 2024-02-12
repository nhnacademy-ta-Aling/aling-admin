package kr.aling.admin.managepost.service.impl;

import kr.aling.admin.common.dto.PageResponseDto;
import kr.aling.admin.managepost.dto.response.ReadManagePostResponseDto;
import kr.aling.admin.managepost.dto.response.ReadManagePostsResponseDto;
import kr.aling.admin.managepost.exception.ManagePostNotFoundException;
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
    public PageResponseDto<ReadManagePostsResponseDto> getManagePosts(String type, Pageable pageable) {
        Page<ReadManagePostsResponseDto> page;
        if (type != null) {
            page = managePostReadRepository.findAllByType(type, pageable);
        } else {
            page = managePostReadRepository.findAllByAll(pageable);
        }
        return new PageResponseDto<>(page.getPageable().getPageNumber(), page.getTotalPages(), page.getTotalElements(), page.getContent());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public ReadManagePostResponseDto getManagePost(long no) {
        if (!managePostReadRepository.existsById(no)) {
            throw new ManagePostNotFoundException(no);
        }
        return managePostReadRepository.findDetailByNo(no);
    }
}
