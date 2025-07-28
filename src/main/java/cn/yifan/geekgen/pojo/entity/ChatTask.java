package cn.yifan.geekgen.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @FileName ChatTask
 * @Description
 * @Author yifan
 * @date 2025-02-25 21:00
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatTask implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String type;

    private String data;

    private String status;

    private Long userId;

    private Long userChatMessageId;

    private Long assistantChatMessageId;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Boolean isDeleted;

    public ChatTask(String type, String data, String status, Long userId) {
        this.type = type;
        this.data = data;
        this.status = status;
        this.userId = userId;
    }

}
