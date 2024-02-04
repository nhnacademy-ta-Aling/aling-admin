package kr.aling.admin.managepost.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import kr.aling.admin.common.valid.anno.ValidEnum;
import kr.aling.admin.managepost.type.ManagePostType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @NotNull
    @Positive
    private Long userNo;

    @ValidEnum(enumClass = ManagePostType.class)
    private String type;

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    private String content;
}
