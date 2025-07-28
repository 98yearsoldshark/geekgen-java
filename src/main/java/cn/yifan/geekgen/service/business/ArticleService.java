package cn.yifan.geekgen.service.business;

import cn.dev33.satoken.stp.StpUtil;
import cn.yifan.geekgen.constant.ArticleIdPrefix;
import cn.yifan.geekgen.constant.CachePrefix;
import cn.yifan.geekgen.exception.ApiError;
import cn.yifan.geekgen.exception.ApiException;
import cn.yifan.geekgen.mapper.ArticleCollectMapper;
import cn.yifan.geekgen.pojo.dto.KeepArticleDTO;
import cn.yifan.geekgen.pojo.entity.ArticleCollect;
import cn.yifan.geekgen.pojo.mongo.Essay;
import cn.yifan.geekgen.pojo.mongo.TextUnit;
import cn.yifan.geekgen.pojo.vo.ArticleTextVO;
import cn.yifan.geekgen.pojo.vo.ArticleFaceVO;
import cn.yifan.geekgen.pojo.vo.KeepArticleTextVO;
import cn.yifan.geekgen.service.base.EssayRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @FileName ArticleService
 * @Description
 * @Author yifan
 * @date 2025-03-01 14:15
 **/

@Slf4j
@Service
public class ArticleService {

    @Autowired
    private EssayRepository essayRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ArticleCollectMapper articleCollectMapper;

    @Cacheable(value = CachePrefix.ESSAY, key = "#id")
    public ArticleTextVO getEssay(String id) {
        Optional<Essay> optionalEssay = essayRepository.findById(id);
        if (optionalEssay.isEmpty()) {
            throw new ApiException(ApiError.ARTICLE_NOT_EXIST);
        }
        Essay essay = optionalEssay.get();
        ArticleTextVO articleTextVO = new ArticleTextVO();
        articleTextVO.setArticleId(ArticleIdPrefix.ESSAY + essay.getId());
        articleTextVO.setBanner(essay.getBanner());
        articleTextVO.setTitle(essay.getTitle());
        articleTextVO.setSubtitle(essay.getSubtitle());
        // tags
        List<String> tags = new ArrayList<>();
        tags.add(essay.getTopic()); // topic
        if (!essay.getLevels().isEmpty()) {
            tags.add(String.join("&", essay.getLevels())); // levels
        }
        tags.add(essay.getWordCount() + "词"); // wordCount
        articleTextVO.setTags(tags);
        articleTextVO.setText(essay.getText());
        return articleTextVO;
    }

    public List<ArticleFaceVO> searchEssay(String level, String topic) {
        //List<Essay> essays = essayRepository.findByTopicAndLevelsContaining(topic, level);

        // 创建一个 MatchOperation，用于筛选符合条件的文档
        MatchOperation matchOperation = Aggregation.match(
                Criteria.where("topic").is(topic).and("levels").in(level)
        );
        // 创建一个 SampleOperation，随机选取 10 个文档
        SampleOperation sampleOperation = Aggregation.sample(10);
        // 创建一个聚合操作
        Aggregation aggregation = Aggregation.newAggregation(matchOperation, sampleOperation);
        // 执行聚合操作并获取结果
        List<Essay> essays = mongoTemplate.aggregate(aggregation, "essay", Essay.class).getMappedResults();

        List<ArticleFaceVO> essayFaceVOS = new ArrayList<>();
        for (Essay essay : essays) {
            ArticleFaceVO essayFaceVO = new ArticleFaceVO();
            essayFaceVO.setArticleId(essay.getId());
            essayFaceVO.setBanner(essay.getBanner());
            essayFaceVO.setTitle(essay.getTitle());
            essayFaceVO.setSubtitle(essay.getSubtitle());
            // tags
            List<String> tags = new ArrayList<>();
            tags.add(essay.getTopic()); // topic
            if (!essay.getLevels().isEmpty()) {
                tags.add(String.join("&", essay.getLevels())); // levels
            }
            tags.add(essay.getWordCount() + "词"); // wordCount
            essayFaceVO.setTags(tags);
            essayFaceVOS.add(essayFaceVO);
        }
        return essayFaceVOS;
    }

    public List<ArticleFaceVO> publishEssay() {
        // 创建一个SampleOperation，随机选取10个文档
        SampleOperation sampleOperation = Aggregation.sample(10);
        // 创建一个聚合操作
        Aggregation aggregation = Aggregation.newAggregation(sampleOperation);
        // 执行聚合操作并获取结果
        List<Essay> essays = mongoTemplate.aggregate(aggregation, "essay", Essay.class).getMappedResults();
        List<ArticleFaceVO> essayFaceVOS = new ArrayList<>();
        for (Essay essay : essays) {
            ArticleFaceVO essayFaceVO = new ArticleFaceVO();
            essayFaceVO.setArticleId(essay.getId());
            essayFaceVO.setBanner(essay.getBanner());
            essayFaceVO.setTitle(essay.getTitle());
            essayFaceVO.setSubtitle(essay.getSubtitle());
            // tags
            List<String> tags = new ArrayList<>();
            tags.add(essay.getTopic()); // topic
            if (!essay.getLevels().isEmpty()) {
                tags.add(String.join("&", essay.getLevels())); // levels
            }
            tags.add(essay.getWordCount() + "词"); // wordCount
            essayFaceVO.setTags(tags);
            essayFaceVOS.add(essayFaceVO);
        }
        return essayFaceVOS;
    }

    public KeepArticleTextVO getKeepArticle(Long id) {
        ArticleCollect articleCollect = articleCollectMapper.getById(id);
        KeepArticleTextVO keepArticleTextVO = new KeepArticleTextVO();
        //keepArticleTextVO.setArticleId(ArticleIdPrefix.KEEP + articleCollect.getId());
        keepArticleTextVO.setArticleId(articleCollect.getArticleId());
        keepArticleTextVO.setBanner(articleCollect.getBanner());
        keepArticleTextVO.setTitle(articleCollect.getTitle());
        keepArticleTextVO.setSubtitle(articleCollect.getSubtitle());
        ObjectMapper mapper = new ObjectMapper();
        // tags
        TypeReference<List<String>> tagTypeReference = new TypeReference<>() {};
        try {
            List<String> tags = mapper.readValue(articleCollect.getTags(), tagTypeReference);
            keepArticleTextVO.setTags(tags);
        } catch (JsonProcessingException e) {
            log.error("获取收藏文章时转换tag为List<String>时失败，错误：{}", e.getMessage());
            throw new ApiException(ApiError.SYSTEM_ERROR);
        }
        // text
        TypeReference<List<List<List<TextUnit>>>> textTypeReference = new TypeReference<>() {};
        try {
            List<List<List<TextUnit>>> text = mapper.readValue(articleCollect.getText(), textTypeReference);
            keepArticleTextVO.setText(text);
        } catch (JsonProcessingException e) {
            log.error("获取收藏文章时转换text为List<List<List<TextUnit>>>时失败，错误：{}", e.getMessage());
            throw new ApiException(ApiError.SYSTEM_ERROR);
        }
        // highlightPositions
        TypeReference<List<List<Integer>>> highlightPositionsTypeReference = new TypeReference<>() {};
        try {
            List<List<Integer>> highlightPositions = mapper.readValue(articleCollect.getHighlightPositions(), highlightPositionsTypeReference);
            keepArticleTextVO.setHighlightPositions(highlightPositions);
        } catch (JsonProcessingException e) {
            log.error("获取收藏文章时转换highlightPositions为List<List<Integer>>时失败，错误：{}", e.getMessage());
            throw new ApiException(ApiError.SYSTEM_ERROR);
        }
        return keepArticleTextVO;
    }

    public List<ArticleFaceVO> getKeepArticles() {
        Long userId = StpUtil.getLoginIdAsLong();
        List<ArticleCollect> articleCollects = articleCollectMapper.getByUserId(userId);
        List<ArticleFaceVO> keepArticleFaceVOS = new ArrayList<>();
        for (ArticleCollect articleCollect : articleCollects) {
            ArticleFaceVO articleFaceVO = new ArticleFaceVO();
            articleFaceVO.setArticleId(articleCollect.getId().toString());
            articleFaceVO.setBanner(articleCollect.getBanner());
            articleFaceVO.setTitle(articleCollect.getTitle());
            articleFaceVO.setSubtitle(articleCollect.getSubtitle());
            ObjectMapper mapper = new ObjectMapper();
            // tags
            TypeReference<List<String>> tagTypeReference = new TypeReference<>() {};
            try {
                List<String> tags = mapper.readValue(articleCollect.getTags(), tagTypeReference);
                articleFaceVO.setTags(tags);
            } catch (JsonProcessingException e) {
                log.error("获取收藏文章时转换tag为List<String>时失败，错误：{}", e.getMessage());
                throw new ApiException(ApiError.SYSTEM_ERROR);
            }
            keepArticleFaceVOS.add(articleFaceVO);
        }
        return keepArticleFaceVOS;
    }

    @Transactional
    public void keepArticle(KeepArticleDTO keepArticleDTO) {
        // 检查是否已经收藏过
        Long userId = StpUtil.getLoginIdAsLong();
        ArticleCollect articleCollect = articleCollectMapper.getByUserIdAndArticleId(userId, keepArticleDTO.getArticleId());
        ObjectMapper mapper = new ObjectMapper();
        if (articleCollect != null) {
            //throw new ApiException(ApiError.ARTICLE_HAD_COLLECTED);
            // 更新高亮
            try {
                String highlightPositions = mapper.writeValueAsString(keepArticleDTO.getHighlightPositions());
                articleCollect.setHighlightPositions(highlightPositions);
                articleCollectMapper.update(articleCollect);
                return;
            } catch (JsonProcessingException e) {
                log.error("保存收藏文章时转换highlightPositions为String时失败，错误：{}", e.getMessage());
                throw new ApiException(ApiError.SYSTEM_ERROR);
            }
        }
        // 保存文章
        // tags
        String tags = null;
        try {
            tags = mapper.writeValueAsString(keepArticleDTO.getTags());
        } catch (JsonProcessingException e) {
            log.error("保存收藏文章时转换tag为String时失败，错误：{}", e.getMessage());
            throw new ApiException(ApiError.SYSTEM_ERROR);
        }
        // text
        String text = null;
        try {
            text = mapper.writeValueAsString(keepArticleDTO.getText());
        } catch (JsonProcessingException e) {
            log.error("保存收藏文章时转换text为String时失败，错误：{}", e.getMessage());
            throw new ApiException(ApiError.SYSTEM_ERROR);
        }
        // highlightPositions
        String highlightPositions = null;
        try {
            highlightPositions = mapper.writeValueAsString(keepArticleDTO.getHighlightPositions());
        } catch (JsonProcessingException e) {
            log.error("保存收藏文章时转换highlightPositions为String时失败，错误：{}", e.getMessage());
            throw new ApiException(ApiError.SYSTEM_ERROR);
        }
        articleCollect = new ArticleCollect(
            keepArticleDTO.getArticleId(), keepArticleDTO.getBanner(), keepArticleDTO.getTitle(),
            keepArticleDTO.getSubtitle(), tags, text, highlightPositions, userId
        );
        articleCollectMapper.insert(articleCollect);
    }


    public void checkKeep(String articleId) {
        Long userId = StpUtil.getLoginIdAsLong();
        ArticleCollect articleCollect = articleCollectMapper.getByUserIdAndArticleId(userId, articleId);
        if (articleCollect == null) {
            throw new ApiException(ApiError.ARTICLE_NOT_COLLECT);
        }
    }

    public void deleteKeep(String id) {

    }
}
