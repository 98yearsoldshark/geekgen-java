package cn.yifan.geekgen.config;

import cn.yifan.geekgen.pojo.rabbit.ConfigModel;
import cn.yifan.geekgen.pojo.rabbit.ConfigQueue;
import cn.yifan.geekgen.pojo.rabbit.LinkedQueue;
import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @FileName RabbitMQConfig
 * @Description
 * @Author yifan
 * @date 2025-01-28 22:55
 **/

@Configuration
@ConfigurationProperties(prefix = "spring.rabbitmq")
@Data
public class RabbitConfig {

    private List<ConfigModel> models;

    @Bean
    public List<LinkedQueue> rabbitQueues() {
        List<LinkedQueue> queues = new ArrayList<>();
        for (ConfigModel model : this.models) {
            for (ConfigQueue configQueue : model.getQueues()) {
                LinkedQueue queue = new LinkedQueue();
                queue.setName(configQueue.getName());
                queue.setRoutingKey(configQueue.getRoutingKey());
                queue.setConcurrency(configQueue.getConcurrency());
                queue.setExchangeName(model.getExchange().getName());
                queues.add(queue);
            }
        }
        return queues;
    }

    @Bean
    public DefaultMessageHandlerMethodFactory handlerMethodFactory() {
        return new DefaultMessageHandlerMethodFactory();
    }

    @Bean
    // 显示声明才能创建
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        for (ConfigModel model : this.models) {
            // 创建交换机
            DirectExchange exchange = new DirectExchange(model.getExchange().getName());
            admin.declareExchange(exchange);
            // 创建队列以及绑定
            for (ConfigQueue configQueue : model.getQueues()) {
                Queue queue = new Queue(configQueue.getName(), true, false, false);
                Binding binding = BindingBuilder.bind(queue).to(exchange).with(configQueue.getRoutingKey());
                admin.declareQueue(queue);
                admin.declareBinding(binding);
            }
        }
        return admin;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
