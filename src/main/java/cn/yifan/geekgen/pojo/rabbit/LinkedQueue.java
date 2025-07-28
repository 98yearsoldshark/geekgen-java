package cn.yifan.geekgen.pojo.rabbit;

import lombok.Data;

/**
 * @FileName LinkedQueue
 * @Description
 * @Author yifan
 * @date 2025-01-29 17:32
 **/

@Data
public class LinkedQueue {

    private String name;
    private String routingKey;
    private String exchangeName;
    private String concurrency;

}
