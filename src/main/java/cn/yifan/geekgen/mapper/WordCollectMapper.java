package cn.yifan.geekgen.mapper;

import cn.yifan.geekgen.pojo.entity.WordCollect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @FileName WordCollectMapper
 * @Description
 * @Author yifan
 * @date 2025-02-25 22:20
 **/

@Mapper
public interface WordCollectMapper {

    WordCollect getById(Long id);

    List<WordCollect> getByUserId(Long userId);

    WordCollect getByUserIdAndWord(Long userId, String word);

    void insert(WordCollect wordCollect);

    void update(WordCollect wordCollect);

}
