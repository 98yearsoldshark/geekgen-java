package cn.yifan.geekgen.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @FileName ChatMessage
 * @Description
 * @Author yifan
 * @date 2025-03-02 16:18
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String role;

    private String content;

    private Long tokens;

    private Long userId;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Boolean isDeleted;

    public ChatMessage(String role, String content, Long tokens, Long userId) {
        this.role = role;
        this.content = content;
        this.tokens = tokens;
        this.userId = userId;
    }

}
