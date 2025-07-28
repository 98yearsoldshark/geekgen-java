package cn.yifan.geekgen.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @FileName ClassTask
 * @Description
 * @Author yifan
 * @date 2025-02-25 21:35
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassTask implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String type;

    private String title;

    private String data;

    private String className;

    private Long classId;

    private Long teacherId;

    private Long studentCount;

    private Long finishCount;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Boolean isDeleted;

    public ClassTask(String type, String title, String data, String className, Long classId, Long teacherId, Long studentCount, Long finishCount) {
        this.type = type;
        this.title = title;
        this.data = data;
        this.className = className;
        this.classId = classId;
        this.teacherId = teacherId;
        this.studentCount = studentCount;
        this.finishCount = finishCount;
    }

}
