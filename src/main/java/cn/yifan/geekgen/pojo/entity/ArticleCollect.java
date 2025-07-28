package cn.yifan.geekgen.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * @FileName ArticleCollect
 * @Description
 * @Author yifan
 * @date 2025-02-25 21:03
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCollect implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String articleId;

    private String banner;

    private String title;

    private String subtitle;

    private String tags;

    private String text;

    private String highlightPositions; // 用户高亮

    private Long userId;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Boolean isDeleted;

    public ArticleCollect(String articleId, String banner, String title, String subtitle, String tags, String text, String highlightPositions, Long userId) {
        this.articleId = articleId;
        this.banner = banner;
        this.title = title;
        this.subtitle = subtitle;
        this.tags = tags;
        this.text = text;
        this.highlightPositions = highlightPositions;
        this.userId = userId;
    }

}
