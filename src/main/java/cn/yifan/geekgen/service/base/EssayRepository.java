package cn.yifan.geekgen.service.base;

import cn.yifan.geekgen.pojo.mongo.Essay;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * @FileName EssayRepository
 * @Description
 * @Author yifan
 * @date 2025-02-28 16:28
 **/

public interface EssayRepository extends MongoRepository<Essay, String> {

    /**
     * 根据 originalId 查询 Essay
     * @param originalId 要查询的 Essay 的 originalId
     * @return 包含查询结果的 Optional 对象，如果未找到则返回空的 Optional
     */
    Essay findByOriginalId(String originalId);

    /**
     * 根据 topic 查询 Essay 列表
     * @param topic 要查询的 Essay 的 topic
     * @return 符合条件的 Essay 列表
     */
    List<Essay> findByTopic(String topic);

    /**
     * 根据 levels 中是否包含特定元素查询 Essay 列表
     * @param level 要查询的特定元素
     * @return 符合条件的 Essay 列表
     */
    List<Essay> findByLevelsContaining(String level);

    /**
     * 同时根据 topic 和 levels 中是否包含特定元素查询 Essay 列表
     * @param topic 要查询的 Essay 的 topic
     * @param level 要查询的特定元素
     * @return 符合条件的 Essay 列表
     */
    List<Essay> findByTopicAndLevelsContaining(String topic, String level);

}
