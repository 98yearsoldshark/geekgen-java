package cn.yifan.geekgen.service.base;

import cn.yifan.geekgen.constant.ApiURL;
import cn.yifan.geekgen.pojo.api.ReviewView;
import cn.yifan.geekgen.pojo.mongo.TextUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @FileName ApiService
 * @Description
 * @Author yifan
 * @date 2025-02-27 23:07
 **/

@Service
public class ApiService {

    @Autowired
    private RestClient restClient;

    public List<List<List<TextUnit>>> textAnalyze(String text) {
        URI uri = UriComponentsBuilder
            .fromUriString(ApiURL.TEXT_ANALYZE_URL)
            .queryParam("text", text)
            .build()
            .toUri();
        // 使用 ParameterizedTypeReference 获取泛型类型信息
        ParameterizedTypeReference<List<List<List<TextUnit>>>> typeRef = new ParameterizedTypeReference<List<List<List<TextUnit>>>>() {};
        // 发送请求并获取响应实体
        ResponseEntity<List<List<List<TextUnit>>>> response = restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(typeRef);
        // 从响应实体中提取响应体
        return response.getBody();
    }

    public String getFSRSReviewCard() {
        return restClient.get()
                .uri(ApiURL.FSRS_REVIEW_CARD_URL)
                .retrieve()
                .toEntity(String.class)
                .getBody();
    }

    public List<ReviewView> getFSRSReviewViews(List<String> cards) {
        URI uri = UriComponentsBuilder
                .fromUriString(ApiURL.FSRS_REVIEW_VIEW_URL)
                .queryParam("cards", cards)
                .build()
                .toUri();

        ParameterizedTypeReference<List<ReviewView>> typeRef = new ParameterizedTypeReference<List<ReviewView>>() {};

        return restClient.get()
               .uri(uri)
               .retrieve()
               .toEntity(typeRef)
               .getBody();
    }

    public String getFSRSReview(String card, Integer rating) {
        URI uri = UriComponentsBuilder
                .fromUriString(ApiURL.FSRS_REVIEW_URL)
                .queryParam("card", card)
                .queryParam("rating", rating)
                .build()
                .toUri();
        return restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(String.class)
                .getBody();
    }

}
