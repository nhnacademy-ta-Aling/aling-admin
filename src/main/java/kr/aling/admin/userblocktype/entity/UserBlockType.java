package kr.aling.admin.userblocktype.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * UserBlockType(회원제재타입) Entity.
 *
 * @author : 이수정
 * @since : 1.0
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_block_type")
@Entity
public class UserBlockType {

    @Id
    @Column(name = "user_block_type_no")
    private Integer userBlockTypeNo;

    @Column(name = "user_block_type_name")
    private String name;
}
