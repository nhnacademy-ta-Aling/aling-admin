package kr.aling.admin.report.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Report(신고) Entity.
 *
 * @author : 이수정
 * @since : 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "report")
@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_no")
    private Long reportNo;

    @Column(name = "aling_user_no")
    private Long userNo;

    @Column(name = "post_no")
    private Long postNo;

    @Column(name = "reply_no")
    private Long replyNo;

    @Column(name = "report_reason")
    private String reason;
}
