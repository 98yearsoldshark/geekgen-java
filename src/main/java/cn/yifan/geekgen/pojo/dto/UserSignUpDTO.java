package cn.yifan.geekgen.pojo.dto;

import cn.yifan.geekgen.annotation.ValueSet;
import cn.yifan.geekgen.constant.UserLevel;
import cn.yifan.geekgen.constant.UserRole;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @FileName SignUpDTO
 * @Description
 * @Author yifan
 * @date 2025-01-28 16:19
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @ValueSet(values = {
        UserLevel.ES, UserLevel.MS, UserLevel.HS, UserLevel.CET4, UserLevel.CET6, UserLevel.PGE
    })
    private String level;

    @ValueSet(values = {UserRole.STUDENT, UserRole.TEACHER})
    private String role;

    @NotBlank
    private String school;

    @NotBlank
    private String name;

}
