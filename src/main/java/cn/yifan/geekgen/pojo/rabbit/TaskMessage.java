package cn.yifan.geekgen.pojo.rabbit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @FileName TaskMessage
 * @Description
 * @Author yifan
 * @date 2025-01-29 21:43
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long taskId;

    private Long userId;

    private String question;

}
