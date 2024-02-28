package kr.aling.admin.common.base;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 생성시간 JPA Auditing을 위한 Abstract class.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseCreateTimeEntity {

    @CreatedDate
    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createAt;
}
