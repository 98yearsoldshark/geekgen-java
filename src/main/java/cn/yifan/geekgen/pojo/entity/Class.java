package cn.yifan.geekgen.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @FileName Class
 * @Description
 * @Author yifan
 * @date 2025-02-25 21:31
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Class implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String code;

    private String name;

    private Long teacherId;

    private String teacherName;

    private Long studentCount;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Boolean isDeleted;

    public Class(String code, String name, Long teacherId, String teacherName, Long studentCount) {
        this.code = code;
        this.name = name;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.studentCount = studentCount;
    }

}
