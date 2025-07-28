package cn.yifan.geekgen.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.yifan.geekgen.pojo.dto.KeepArticleDTO;
import cn.yifan.geekgen.pojo.vo.ArticleTextVO;
import cn.yifan.geekgen.pojo.vo.ArticleFaceVO;
import cn.yifan.geekgen.pojo.vo.KeepArticleTextVO;
import cn.yifan.geekgen.service.business.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @FileName ArticleController
 * @Description
 * @Author yifan
 * @date 2025-03-01 14:14
 **/

@RestController
@RequestMapping("/article")
@SaCheckLogin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/essays/{id}")
    public ArticleTextVO getEssay(@PathVariable String id) {
        return articleService.getEssay(id);
    }

    @GetMapping("/essays/search")
    public List<ArticleFaceVO> searchEssay(@RequestParam("level") String level, @RequestParam("topic") String topic) {
        return articleService.searchEssay(level, topic);
    }

    @GetMapping("/essays/publish")
    public List<ArticleFaceVO> publishEssay() {
        return articleService.publishEssay();
    }

    @GetMapping("/keeps/{id}")
    public KeepArticleTextVO getKeepArticle(@PathVariable Long id) {
        return articleService.getKeepArticle(id);
    }

    @GetMapping("/keeps")
    public List<ArticleFaceVO> getKeepArticles() {
        return articleService.getKeepArticles();
    }

    @PostMapping("/keeps")
    public void keepArticle(@RequestBody KeepArticleDTO keepArticleDTO) {
        articleService.keepArticle(keepArticleDTO);
    }

    @GetMapping("/keeps/check")
    public void checkKeep(@RequestParam("articleId") String articleId) {
        articleService.checkKeep(articleId);
    }

    @DeleteMapping("/keeps/{id}")
    public void deleteKeep(@PathVariable String id) {
        articleService.deleteKeep(id);
    }

}
