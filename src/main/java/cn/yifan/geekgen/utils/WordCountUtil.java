package cn.yifan.geekgen.utils;

import cn.yifan.geekgen.pojo.mongo.TextUnit;

import java.util.List;

/**
 * @FileName WordCountUtil
 * @Description
 * @Author yifan
 * @date 2025-02-28 22:24
 **/

public class WordCountUtil {

    public static Integer countWords(List<List<List<TextUnit>>> text) {
        int count = 0;
        for (List<List<TextUnit>> paragraph : text) {
            for (List<TextUnit> sentence : paragraph) {
                for (TextUnit word : sentence) {
                    count += 1;
                }
            }
        }
        return count;
    }

}
