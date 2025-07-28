package cn.yifan.geekgen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @FileName TaskVO
 * @Description
 * @Author yifan
 * @date 2025-02-28 18:42
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String code;

    private String name;

    private String teacherName;

    private Long studentCount;

}
