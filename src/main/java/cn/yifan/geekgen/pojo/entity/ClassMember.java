package cn.yifan.geekgen.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @FileName ClassMember
 * @Description
 * @Author yifan
 * @date 2025-02-25 21:43
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassMember implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long classId;

    private Long userId;

    private String role;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Boolean isDeleted;

    public ClassMember(Long classId, Long userId, String role) {
        this.classId = classId;
        this.userId = userId;
        this.role = role;
    }

}
