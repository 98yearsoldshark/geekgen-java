package cn.yifan.geekgen.pojo.vo;

import cn.yifan.geekgen.pojo.api.ReviewChoiceData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * @FileName WordReviewViewVO
 * @Description
 * @Author yifan
 * @date 2025-03-01 14:02
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordReviewViewVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String word;

    private Integer due;

    private Integer last;

    private Map<String, ReviewChoiceData> reviewChoices;

}
