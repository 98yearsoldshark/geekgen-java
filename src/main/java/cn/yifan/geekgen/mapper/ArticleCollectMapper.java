package cn.yifan.geekgen.mapper;

import cn.yifan.geekgen.pojo.entity.ArticleCollect;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @FileName ArticleCollectMapper
 * @Description
 * @Author yifan
 * @date 2025-02-25 22:26
 **/

@Mapper
public interface ArticleCollectMapper {

    ArticleCollect getById(Long id);

    List<ArticleCollect> getByUserId(Long userId);

    ArticleCollect getByUserIdAndArticleId(Long userId, String articleId);

    void insert(ArticleCollect articleCollect);

    void update(ArticleCollect articleCollect);

}
