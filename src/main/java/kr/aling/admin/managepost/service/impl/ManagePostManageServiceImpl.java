package kr.aling.admin.managepost.service.impl;

import java.util.Objects;
import kr.aling.admin.common.annotation.ManageService;
import kr.aling.admin.common.feign.UserFeignClient;
import kr.aling.admin.managepost.dto.request.CreateManagePostRequestDto;
import kr.aling.admin.managepost.dto.request.ModifyManagePostRequestDto;
import kr.aling.admin.managepost.dto.response.CreateManagePostResponseDto;
import kr.aling.admin.managepost.entity.ManagePost;
import kr.aling.admin.managepost.exception.ManagePostAlreadyDeletedException;
import kr.aling.admin.managepost.exception.ManagePostNotFoundException;
import kr.aling.admin.managepost.repository.ManagePostManageRepository;
import kr.aling.admin.managepost.service.ManagePostManageService;
import kr.aling.admin.user.dto.response.IsExistsUserResponseDto;
import kr.aling.admin.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

/**
 * 관리게시글 CUD Service 구현체.
 *
 * @author 이수정
 * @since 1.0
 */
@RequiredArgsConstructor
@ManageService
public class ManagePostManageServiceImpl implements ManagePostManageService {

    private final ManagePostManageRepository managePostManageRepository;

    private final UserFeignClient userFeignClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateManagePostResponseDto registerManagePost(CreateManagePostRequestDto requestDto) {
        ResponseEntity<IsExistsUserResponseDto> response = userFeignClient.isExistsUser(requestDto.getUserNo());
        if (Objects.requireNonNull(response.getBody()).getIsExists().equals(Boolean.FALSE)) {
            throw new UserNotFoundException(requestDto.getUserNo());
        }

        ManagePost managePost = ManagePost.builder()
                .userNo(requestDto.getUserNo())
                .type(requestDto.getType())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();
        managePost = managePostManageRepository.save(managePost);
        return new CreateManagePostResponseDto(managePost.getManagePostNo());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void modifyManagePost(Long managePostNo, ModifyManagePostRequestDto requestDto) {
        ManagePost managePost = managePostManageRepository.findById(managePostNo)
                .orElseThrow(() -> new ManagePostNotFoundException(managePostNo));
        managePost.modifyPost(requestDto.getType(), requestDto.getTitle(), requestDto.getContent());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteManagePost(Long managePostNo) {
        ManagePost managePost = managePostManageRepository.findById(managePostNo)
                .orElseThrow(() -> new ManagePostNotFoundException(managePostNo));

        if (Boolean.TRUE.equals(managePost.getIsDelete())) {
            throw new ManagePostAlreadyDeletedException();
        }
        managePost.reverseIsDelete();
    }
}
