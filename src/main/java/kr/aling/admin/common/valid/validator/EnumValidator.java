package kr.aling.admin.common.valid.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import kr.aling.admin.common.valid.anno.ValidEnum;

/**
 * Enum 검증 로직을 정의한 Validator Class.
 *
 * @author : 이수정
 * @since : 1.0
 */
public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private ValidEnum constraintAnnotation;

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Enum<?>[] enumValues = constraintAnnotation.enumClass().getEnumConstants();
        if (value != null && enumValues != null) {
            for (Enum<?> enumValue : enumValues) {
                if (value.equals(enumValue.name())) {
                    return true;
                }
            }
        }
        return false;
    }
}
