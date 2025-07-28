package cn.yifan.geekgen.utils;

import java.util.Random;

/**
 * @FileName ClassCodeUtil
 * @Description
 * @Author yifan
 * @date 2025-02-28 19:11
 **/
public class ClassCodeUtil {

    public static String generateClassCode() {
        // 定义包含所有大写字母和数字的字符集合
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        // 创建 Random 对象用于生成随机数
        Random random = new Random();
        StringBuilder classCode = new StringBuilder();

        // 循环 6 次，每次随机选取一个字符添加到班级码中
        for (int i = 0; i < 6; i++) {
            // 生成一个 0 到 characters 长度减 1 之间的随机索引
            int index = random.nextInt(characters.length());
            // 根据随机索引从 characters 中取出一个字符
            char randomChar = characters.charAt(index);
            // 将随机字符添加到班级码中
            classCode.append(randomChar);
        }

        // 将 StringBuilder 对象转换为字符串并返回
        return classCode.toString();
    }

}
