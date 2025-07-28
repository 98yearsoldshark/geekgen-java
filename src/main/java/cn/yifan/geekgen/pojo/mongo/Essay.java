package cn.yifan.geekgen.pojo.mongo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @FileName Essay
 * @Description
 * @Author yifan
 * @date 2025-02-28 16:22
 **/

@Document(collection = "essay")
@Data
@NoArgsConstructor
public class Essay {

    @Id
    private String id;

    private String originalId;

    private String banner;

    private String title;

    private String subtitle;

    private String topic;

    private List<List<List<TextUnit>>> text;

    private Integer wordCount;

    private Integer difficultyScore;

    private List<String> levels;

}
