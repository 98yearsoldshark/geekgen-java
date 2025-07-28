package cn.yifan.geekgen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @FileName CreateChatTaskVO
 * @Description
 * @Author yifan
 * @date 2025-02-26 13:55
 **/

@Data
@AllArgsConstructor
public class CreateChatTaskVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long taskId;

}
