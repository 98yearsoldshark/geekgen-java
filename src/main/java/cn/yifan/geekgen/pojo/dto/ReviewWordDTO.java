package cn.yifan.geekgen.pojo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @FileName ReviewWordDTO
 * @Description
 * @Author yifan
 * @date 2025-03-01 14:12
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewWordDTO {

    @NotBlank
    private String word;

    @Max(4)
    @Min(1)
    private Integer rating;

}
