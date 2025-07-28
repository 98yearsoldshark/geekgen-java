package cn.yifan.geekgen.pojo.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * @FileName ReviewView
 * @Description
 * @Author yifan
 * @date 2025-02-28 16:55
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewView implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer due;

    private Integer last;

    private Map<String, ReviewChoiceData> reviewChoices;

}
