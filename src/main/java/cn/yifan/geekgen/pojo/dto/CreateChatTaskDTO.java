package cn.yifan.geekgen.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @FileName CreateChatTaskDTO
 * @Description
 * @Author yifan
 * @date 2025-02-26 13:54
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatTaskDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String question;

}
