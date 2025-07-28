package cn.yifan.geekgen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @FileName StudentClassTaskVO
 * @Description
 * @Author yifan
 * @date 2025-02-28 22:38
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentClassTaskVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String type;

    private String title;

    private String className;

    private String time;

    private String status;

}
