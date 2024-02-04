package kr.aling.admin.managepost.service.impl;

import javax.transaction.Transactional;
import kr.aling.admin.managepost.dto.request.CreateManagePostRequestDto;
import kr.aling.admin.managepost.dto.response.CreateManagePostResponseDto;
import kr.aling.admin.managepost.entity.ManagePost;
import kr.aling.admin.managepost.repository.ManagePostManageRepository;
import kr.aling.admin.managepost.service.ManagePostManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 관리 게시글 CUD Service 구현체.
 *
 * @author : 이수정
 * @since : 1.0
 */
@RequiredArgsConstructor
@Transactional
@Service
public class ManagePostManageServiceImpl implements ManagePostManageService {

    private final ManagePostManageRepository managePostManageRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateManagePostResponseDto registerManagePost(CreateManagePostRequestDto requestDto) {
        ManagePost managePost = ManagePost.builder()
                .userNo(requestDto.getUserNo())
                .type(requestDto.getType())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();
        managePost = managePostManageRepository.save(managePost);
        return new CreateManagePostResponseDto(managePost.getManagePostNo());
    }
}
