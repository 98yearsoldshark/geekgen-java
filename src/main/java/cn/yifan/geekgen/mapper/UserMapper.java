package cn.yifan.geekgen.mapper;

import cn.yifan.geekgen.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @FileName UserMapper
 * @Description
 * @Author yifan
 * @date 2025-02-24 19:39
 **/

@Mapper
public interface UserMapper {

    User getById(Long id);

    User getByUsername(String username);

    void insert(User user);

}
