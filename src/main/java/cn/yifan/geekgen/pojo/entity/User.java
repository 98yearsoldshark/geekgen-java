package cn.yifan.geekgen.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @FileName User
 * @Description
 * @Author yifan
 * @date 2025-02-25 20:57
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String password;

    private String level;

    private String role;

    private String school;

    private String name;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    private Boolean isDeleted;

    public User(String username, String password, String level, String role, String school, String name) {
        this.username = username;
        this.password = password;
        this.level = level;
        this.role = role;
        this.school = school;
        this.name = name;
    }

}
