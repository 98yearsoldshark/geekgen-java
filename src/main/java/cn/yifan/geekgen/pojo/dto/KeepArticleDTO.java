package cn.yifan.geekgen.pojo.dto;

import cn.yifan.geekgen.pojo.mongo.TextUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @FileName KeepArticleDTO
 * @Description
 * @Author yifan
 * @date 2025-03-01 16:03
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeepArticleDTO {

    private String articleId; // 有前缀

    private String banner;

    private String title;

    private String subtitle;

    private List<String> tags;

    private List<List<List<TextUnit>>> text;

    private List<List<Integer>> highlightPositions; // 用户高亮

}
