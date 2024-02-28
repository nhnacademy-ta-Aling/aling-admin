package kr.aling.admin.common.valid.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import kr.aling.admin.common.valid.validator.EnumValidator;

/**
 * Enum으로 정의된 요청값을 검증하기 위한 Annotation.
 *
 * @author 이수정
 * @since 1.0
 */
@Documented
@Constraint(validatedBy = EnumValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {

    /**
     * 제약조건 검증 실패 시 오류 메세지.
     *
     * @return 검증 실패 메세지
     * @author 이수정
     * @since 1.0
     */
    String message() default "Invalid type.";

    /**
     * 검증 대상 그룹 사용자 정의 가능.
     *
     * @return 대상 그룹 Class 배열
     * @author 이수정
     * @since 1.0
     */
    Class<?>[] groups() default {};

    /**
     * 추가 정보 전달 가능. (ex. 심각도)
     *
     * @return Payload Class 배열
     * @author 이수정
     * @since 1.0
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * 검증 enum Class.
     *
     * @return 검증 enum Class
     * @author 이수정
     * @since 1.0
     */
    Class<? extends Enum<?>> enumClass();
}
