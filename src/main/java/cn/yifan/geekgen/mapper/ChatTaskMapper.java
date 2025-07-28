package cn.yifan.geekgen.mapper;

import cn.yifan.geekgen.pojo.entity.ChatTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @FileName ChatTaskMapper
 * @Description
 * @Author yifan
 * @date 2025-02-25 22:13
 **/

@Mapper
public interface ChatTaskMapper {

    ChatTask getById(Long id);

    List<ChatTask> getByUserId(Long userId);

    void insert(ChatTask chatTask);

    void update(ChatTask chatTask);

}
