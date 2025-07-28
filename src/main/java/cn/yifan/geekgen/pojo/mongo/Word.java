package cn.yifan.geekgen.pojo.mongo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

/**
 * @FileName Word
 * @Description
 * @Author yifan
 * @date 2025-02-28 16:14
 **/

@Document(collection = "word")
@Data
@NoArgsConstructor
public class Word {

    @Id
    private String id;

    private String word;

    private Map<String, Object> core;

    private Map<String, Object> relation;

    private Map<String, Object> ai;

    private List<Object> articles;

}
