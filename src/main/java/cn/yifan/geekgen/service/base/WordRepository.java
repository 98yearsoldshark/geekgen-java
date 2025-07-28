package cn.yifan.geekgen.service.base;

import cn.yifan.geekgen.pojo.mongo.Word;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @FileName WordRepository
 * @Description
 * @Author yifan
 * @date 2025-02-28 16:18
 **/

public interface WordRepository extends MongoRepository<Word, String> {

    /**
     * 根据 word 字段查询 Word 对象
     * @param word 要查询的单词
     * @return 包含查询结果的 Optional 对象，如果未找到则返回空的 Optional
     */
    Word findByWord(String word);

}
