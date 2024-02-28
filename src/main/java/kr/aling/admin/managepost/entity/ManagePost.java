package kr.aling.admin.managepost.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import kr.aling.admin.common.base.BaseCreateTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * ManagePost(관리 게시글) Entity.
 *
 * @author 이수정
 * @since 1.0
 */
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "manage_post")
@Entity
public class ManagePost extends BaseCreateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manage_post_no")
    private Long managePostNo;

    @Column(name = "aling_user_no")
    private Long userNo;

    @Column(name = "manage_post_type")
    private String type;

    @Column(name = "manage_post_title")
    private String title;

    @Column(name = "manage_post_content")
    private String content;

    @Column(name = "manage_post_is_delete")
    private Boolean isDelete;

    @PrePersist
    public void prePersist() {
        isDelete = !Objects.isNull(isDelete) && isDelete;
    }

    public void modifyPost(String type, String title, String content) {
        this.type = type;
        this.title = title;
        this.content = content;
    }

    public void reverseIsDelete() {
        this.isDelete = !this.isDelete;
    }
}
