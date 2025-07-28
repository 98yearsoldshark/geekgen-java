package cn.yifan.geekgen.pojo.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * @FileName ReviewChoice
 * @Description
 * @Author yifan
 * @date 2025-02-28 16:55
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewChoiceData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer rating;

    private Integer due;

}
