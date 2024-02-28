package kr.aling.admin.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * org.springframework 의 Service 어노테이션, transaction.annotation.Transactional (readOnly) 어노테이션을 포함하는 Annotation.
 *
 * @author 이수정
 * @since 1.0
 */
@Target(value = {ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional(readOnly = true)
@Service
public @interface ReadService {

}
