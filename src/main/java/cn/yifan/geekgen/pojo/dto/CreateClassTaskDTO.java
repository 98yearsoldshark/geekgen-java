package cn.yifan.geekgen.pojo.dto;

import cn.yifan.geekgen.annotation.ValueSet;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @FileName CreateClassTaskDTO
 * @Description
 * @Author yifan
 * @date 2025-02-28 18:46
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClassTaskDTO {

    private String classCode;

    @ValueSet(values = {"article"})
    private String type;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

}
