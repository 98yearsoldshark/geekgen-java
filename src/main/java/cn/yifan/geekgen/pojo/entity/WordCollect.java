package cn.yifan.geekgen.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @FileName WordCollect
 * @Description
 * @Author yifan
 * @date 2025-02-25 21:02
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WordCollect implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String word;

    private String data;

    private Long userId;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Boolean isDeleted;

    public WordCollect(String word, String data, Long userId) {
        this.word = word;
        this.data = data;
        this.userId = userId;
    }

}
