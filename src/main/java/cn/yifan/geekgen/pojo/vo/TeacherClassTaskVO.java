package cn.yifan.geekgen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @FileName TeacherClassTaskVO
 * @Description
 * @Author yifan
 * @date 2025-02-28 18:48
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherClassTaskVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String type;

    private String title;

    private String className;

    private Long studentCount;

    private Long finishCount;

    private String time;

}
