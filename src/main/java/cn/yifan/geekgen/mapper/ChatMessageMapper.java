package cn.yifan.geekgen.mapper;

import cn.yifan.geekgen.pojo.entity.ChatMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * @FileName ChatMessageMapper
 * @Description 
 * @Author yifan
 * @date 2025-03-02 16:21
 **/

@Mapper
public interface ChatMessageMapper {

    void insert(ChatMessage chatMessage);

    void update(ChatMessage chatMessage);

}
