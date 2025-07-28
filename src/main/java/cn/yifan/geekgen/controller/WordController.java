package cn.yifan.geekgen.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.yifan.geekgen.pojo.dto.CollectWordDTO;
import cn.yifan.geekgen.pojo.dto.ReviewWordDTO;
import cn.yifan.geekgen.pojo.vo.WordReviewViewVO;
import cn.yifan.geekgen.pojo.vo.WordVO;
import cn.yifan.geekgen.service.business.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @FileName WordController
 * @Description
 * @Author yifan
 * @date 2025-03-01 13:24
 **/

@RestController
@RequestMapping("/word")
@SaCheckLogin
public class WordController {

    @Autowired
    private WordService wordService;

    @GetMapping("/data")
    public WordVO getWordData(@RequestParam("word") String word) {
        return wordService.getWordData(word);
    }

    @GetMapping("/review/check")
    public void checkReview(@RequestParam("word") String word) {
        wordService.checkReview(word);
    }

    @PostMapping("/review/collect")
    public void collectWord(@RequestBody CollectWordDTO collectWordDTO) {
        wordService.collectWord(collectWordDTO);
    }

    @PostMapping("/review")
    public void reviewWord(@RequestBody @Validated ReviewWordDTO reviewWordDTO) {
        wordService.reviewWord(reviewWordDTO);
    }

    @GetMapping("/review")
    public List<WordReviewViewVO> getReviewWords() {
        return wordService.getReviewWords();
    }

}
