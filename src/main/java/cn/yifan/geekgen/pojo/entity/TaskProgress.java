package cn.yifan.geekgen.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @FileName TaskProgress
 * @Description
 * @Author yifan
 * @date 2025-02-25 21:49
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskProgress implements Serializable {

    private Long id;

    private Long taskId;

    private String taskTitle;

    private String taskType;

    private Long userId;

    private String userName;

    private String className;

    private String status;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Boolean isDeleted;

    public TaskProgress(Long taskId, String taskTitle, String taskType, Long userId, String userName, String className, String status) {
        this.taskId = taskId;
        this.taskTitle = taskTitle;
        this.taskType = taskType;
        this.userId = userId;
        this.userName = userName;
        this.className = className;
        this.status = status;
    }

}
