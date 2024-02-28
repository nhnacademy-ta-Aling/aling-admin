package kr.aling.admin.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * org.springframework 의 stereotype.Service 어노테이션, transaction.annotation.Transactional 어노테이션을 포함하는 Annotation.
 *
 * @author 이수정
 * @since 1.0
 */
@Target(value = ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Service
@Transactional
public @interface ManageService {

}
