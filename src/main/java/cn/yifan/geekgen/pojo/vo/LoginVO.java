package cn.yifan.geekgen.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @FileName LoginVO
 * @Description
 * @Author yifan
 * @date 2025-02-26 14:59
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String token;

    private String role;

    private String level;

}
