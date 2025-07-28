package cn.yifan.geekgen.pojo.vo;

import cn.yifan.geekgen.pojo.mongo.TextUnit;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @FileName KeepArticleTextVO
 * @Description
 * @Author yifan
 * @date 2025-03-01 15:42
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeepArticleTextVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String articleId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String banner;

    private String title;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String subtitle;

    private List<String> tags;

    private List<List<List<TextUnit>>> text;

    private List<List<Integer>> highlightPositions;

}
