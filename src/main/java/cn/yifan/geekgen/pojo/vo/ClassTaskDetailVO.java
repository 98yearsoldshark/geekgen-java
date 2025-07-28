package cn.yifan.geekgen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @FileName ClassTaskDetailVO
 * @Description
 * @Author yifan
 * @date 2025-02-28 18:55
 **/

@Data
@AllArgsConstructor
public class ClassTaskDetailVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String articleId;

    private Integer time;

}
