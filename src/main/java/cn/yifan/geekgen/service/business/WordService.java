package cn.yifan.geekgen.service.business;

import cn.dev33.satoken.stp.StpUtil;
import cn.yifan.geekgen.constant.CachePrefix;
import cn.yifan.geekgen.exception.ApiError;
import cn.yifan.geekgen.exception.ApiException;
import cn.yifan.geekgen.mapper.WordCollectMapper;
import cn.yifan.geekgen.pojo.api.ReviewView;
import cn.yifan.geekgen.pojo.dto.CollectWordDTO;
import cn.yifan.geekgen.pojo.dto.ReviewWordDTO;
import cn.yifan.geekgen.pojo.entity.WordCollect;
import cn.yifan.geekgen.pojo.mongo.Word;
import cn.yifan.geekgen.pojo.vo.WordReviewViewVO;
import cn.yifan.geekgen.pojo.vo.WordVO;
import cn.yifan.geekgen.service.base.ApiService;
import cn.yifan.geekgen.service.base.WordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName WordService
 * @Description
 * @Author yifan
 * @date 2025-03-01 13:24
 **/

@Slf4j
@Service
public class WordService {

    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private WordCollectMapper wordCollectMapper;
    @Autowired
    private ApiService apiService;

    @Cacheable(value = CachePrefix.WORD, key = "#word")
    public WordVO getWordData(String word) {
        Word wordData = wordRepository.findByWord(word);
        if (wordData == null) {
            throw new ApiException(ApiError.WORD_NOT_EXIST);
        }
        WordVO wordVO = new WordVO();
        BeanUtils.copyProperties(wordData, wordVO);
        return wordVO;
    }

    public void checkReview(String word) {
        Long userId = StpUtil.getLoginIdAsLong();
        WordCollect wordCollect = wordCollectMapper.getByUserIdAndWord(userId, word);
        if (wordCollect == null) {
            throw new ApiException(ApiError.WORD_NOT_COLLECT);
        }
    }

    public void collectWord(CollectWordDTO collectWordDTO) {
        // 校验单词是否存在
        Word wordData = wordRepository.findByWord(collectWordDTO.getWord());
        if (wordData == null) {
            throw new ApiException(ApiError.WORD_NOT_EXIST);
        }
        // 校验单词是否已被收藏
        Long userId = StpUtil.getLoginIdAsLong();
        WordCollect wordCollect = wordCollectMapper.getByUserIdAndWord(userId, collectWordDTO.getWord());
        if (wordCollect != null) {
            throw new ApiException(ApiError.WORD_HAD_COLLECTED);
        }
        // 收藏单词
        String data = apiService.getFSRSReviewCard();
        wordCollect = new WordCollect(
            collectWordDTO.getWord(), data, userId
        );
        wordCollectMapper.insert(wordCollect);
    }

    public void reviewWord(ReviewWordDTO reviewWordDTO) {
        // 校验单词是否被收藏
        Long userId = StpUtil.getLoginIdAsLong();
        WordCollect wordCollect = wordCollectMapper.getByUserIdAndWord(userId, reviewWordDTO.getWord());
        if (wordCollect == null) {
            throw new ApiException(ApiError.WORD_NOT_COLLECT);
        }
        // 复习单词
        String newData = apiService.getFSRSReview(wordCollect.getData(), reviewWordDTO.getRating());
        wordCollect.setData(newData);
        wordCollectMapper.update(wordCollect);
    }

    public List<WordReviewViewVO> getReviewWords() {
        Long userId = StpUtil.getLoginIdAsLong();
        List<WordCollect> wordCollects = wordCollectMapper.getByUserId(userId);
        List<WordReviewViewVO> wordReviewViewVOS = new ArrayList<>();
        if (wordCollects.isEmpty()) {
            return wordReviewViewVOS;
        }
        List<String> cards = wordCollects.stream().map(WordCollect::getData).toList();
        List<ReviewView> fsrsReviewViews = apiService.getFSRSReviewViews(cards);
        for (int i = 0; i < fsrsReviewViews.size(); i++) {
            ReviewView reviewView = fsrsReviewViews.get(i);
            WordReviewViewVO wordReviewViewVO = new WordReviewViewVO();
            BeanUtils.copyProperties(reviewView, wordReviewViewVO);
            wordReviewViewVO.setWord(wordCollects.get(i).getWord());
            wordReviewViewVOS.add(wordReviewViewVO);
        }
        return wordReviewViewVOS;
    }
}
