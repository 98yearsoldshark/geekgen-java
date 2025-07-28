package cn.yifan.geekgen.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @FileName TimeUtil
 * @Description
 * @Author yifan
 * @date 2025-02-28 22:21
 **/

public class TimeUtil {

    public static String formatTimestamp(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        // 将Timestamp转换为LocalDateTime
        Instant instant = timestamp.toInstant();
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        // 定义日期格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        // 将LocalDateTime对象格式化为字符串
        return localDateTime.format(formatter);
    }

}
