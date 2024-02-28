package kr.aling.admin.userblocklog.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import kr.aling.admin.userblocktype.entity.UserBlockType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * UserBlockLog(회원제재로그) Entity.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_block_log")
@Entity
public class UserBlockLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_block_no")
    private Long userBlockNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_block_type_no")
    private UserBlockType userBlockType;

    @Column(name = "aling_user_no")
    private Long userNo;

    @Column(name = "user_block_start_at")
    private LocalDateTime startAt;

    @Column(name = "user_block_end_at")
    private LocalDateTime endAt;

    @Column(name = "user_block_reason")
    private String reason;
}
