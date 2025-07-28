package cn.yifan.geekgen.pojo.rabbit;

import lombok.Data;

/**
 * @FileName ConfigQueue
 * @Description
 * @Author yifan
 * @date 2025-01-29 17:32
 **/

@Data
public class ConfigQueue {

    private String name;
    private String routingKey;
    private String concurrency = "5-10";

}
