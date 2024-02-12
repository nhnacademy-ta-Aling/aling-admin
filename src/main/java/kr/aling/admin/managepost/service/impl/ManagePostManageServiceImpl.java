package kr.aling.admin.managepost.service.impl;

import java.net.URI;
import java.util.Objects;
import javax.transaction.Transactional;
import kr.aling.admin.managepost.dto.request.CreateManagePostRequestDto;
import kr.aling.admin.managepost.dto.response.CreateManagePostResponseDto;
import kr.aling.admin.managepost.entity.ManagePost;
import kr.aling.admin.managepost.repository.ManagePostManageRepository;
import kr.aling.admin.managepost.service.ManagePostManageService;
import kr.aling.admin.user.dto.request.IsExistsUserRequestDto;
import kr.aling.admin.user.dto.response.IsExistsUserResponseDto;
import kr.aling.admin.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 관리게시글 CUD Service 구현체.
 *
 * @author : 이수정
 * @since : 1.0
 */
@RequiredArgsConstructor
@Transactional
@Service
public class ManagePostManageServiceImpl implements ManagePostManageService {

    @Value("${aling.host}")
    private String host;

    private final ManagePostManageRepository managePostManageRepository;

    private final RestTemplate restTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public CreateManagePostResponseDto registerManagePost(CreateManagePostRequestDto requestDto) {
        URI uri = UriComponentsBuilder.fromPath("/api/v1/users/check/" + requestDto.getUserNo())
                .scheme("http").host(host).port(9020)
                .build(false).encode().toUri();

        HttpEntity<IsExistsUserRequestDto> httpEntity = new HttpEntity<>(new HttpHeaders());

        ResponseEntity<IsExistsUserResponseDto> response =
                restTemplate.exchange(uri, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<>() {
                });
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
}
