package kr.aling.admin.managepost.controller;

import javax.validation.Valid;
import kr.aling.admin.common.exception.CustomException;
import kr.aling.admin.managepost.dto.request.CreateManagePostRequestDto;
import kr.aling.admin.managepost.dto.request.ModifyManagePostRequestDto;
import kr.aling.admin.managepost.dto.response.CreateManagePostResponseDto;
import kr.aling.admin.managepost.service.ManagePostManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 관리게시글 CUD RestController.
 *
 * @author 이수정
 * @since 1.0
 */
@RequiredArgsConstructor
@RequestMapping("/api/v1/manage-posts")
@RestController
public class ManagePostManageController {

    private final ManagePostManageService managePostManageService;

    /**
     * 관리게시글 등록 요청을 처리합니다.
     *
     * @param requestDto    관리게시글 등록에 필요한 요청 파라미터를 담은 Dto
     * @param bindingResult 요청 파라미터 검증 오류를 보관하는 객체
     * @return 관리게시글 번호 반환
     * @author 이수정
     * @since 1.0
     */
    @PostMapping
    public ResponseEntity<CreateManagePostResponseDto> registerManagePost(
            @Valid @RequestBody CreateManagePostRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    "register manage post valid error - " + bindingResult.getAllErrors());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(managePostManageService.registerManagePost(requestDto));
    }

    /**
     * 관리게시글 수정 요청을 처리합니다.
     *
     * @param managePostNo  관리게시글 번호
     * @param requestDto    관리게시글 수정에 필요한 요청 파라미터를 담은 Dto
     * @param bindingResult 요청 파라미터 검증 오류를 보관하는 객체
     * @return 200 OK
     * @author 이수정
     * @since 1.0
     */
    @PutMapping("/{managePostNo}")
    public ResponseEntity<Void> modifyManagePost(@PathVariable Long managePostNo,
            @Valid @RequestBody ModifyManagePostRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    "modify manage post valid error - " + bindingResult.getAllErrors());
        }
        managePostManageService.modifyManagePost(managePostNo, requestDto);
        return ResponseEntity.ok().build();
    }

    /**
     * 관리게시글 삭제 요청을 처리합니다.
     *
     * @param managePostNo 관리게시글 번호
     * @return 204 No content
     * @author 이수정
     * @since 1.0
     */
    @DeleteMapping("/{managePostNo}")
    public ResponseEntity<Void> deleteManagePost(@PathVariable Long managePostNo) {
        managePostManageService.deleteManagePost(managePostNo);
        return ResponseEntity.noContent().build();
    }
}
