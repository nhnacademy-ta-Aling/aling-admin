package kr.aling.admin.managepost.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import kr.aling.admin.managepost.dto.valid.ManagePostType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import kr.aling.admin.common.valid.anno.ValidEnum;

/**
 * 관리 게시글 생성 요청 파라미터를 담는 Dto.
 *
 * @author : 이수정
 * @since : 1.0
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreateManagePostRequestDto {

    @ValidEnum(enumClass = ManagePostType.class)
    private ManagePostType managePostType;

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    private String content;
}
