package cn.yifan.geekgen.config;

import cn.yifan.geekgen.pojo.rabbit.LinkedQueue;
import cn.yifan.geekgen.pojo.rabbit.TaskMessage;
import cn.yifan.geekgen.worker.AITaskWorker;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import java.util.List;

/**
 * @FileName RabbitListenerConfig
 * @Description 
 * @Author yifan
 * @date 2025-01-29 13:25
 **/

@Configuration
@Slf4j
public class RabbitListenerConfig implements RabbitListenerConfigurer {

    private final List<LinkedQueue> rabbitQueues;
    private final DefaultMessageHandlerMethodFactory handlerMethodFactory;
    private final MessageConverter jsonMessageConverter;
    private final AITaskWorker aiTaskWorker;

    public RabbitListenerConfig(
            List<LinkedQueue> rabbitQueues,
            DefaultMessageHandlerMethodFactory handlerMethodFactory,
            MessageConverter jsonMessageConverter,
            AITaskWorker aiTaskWorker
    ) {
        this.rabbitQueues = rabbitQueues;
        this.handlerMethodFactory = handlerMethodFactory;
        this.jsonMessageConverter = jsonMessageConverter;
        this.aiTaskWorker = aiTaskWorker;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(handlerMethodFactory);

        for (LinkedQueue queue : rabbitQueues) {
            SimpleRabbitListenerEndpoint endpoint = new SimpleRabbitListenerEndpoint();
            endpoint.setId(queue.getName() + "_endpoint");
            endpoint.setQueueNames(queue.getName());
            endpoint.setConcurrency(queue.getConcurrency());
            //endpoint.setMessageConverter(jsonMessageConverter);
            endpoint.setMessageListener(message -> {
                Object data = jsonMessageConverter.fromMessage(message);
                if (data instanceof TaskMessage taskMessage) {
                    // 处理消息的逻辑
                    log.info("AI-Task-Worker消费任务，线程：{}, Received taskId: {}", Thread.currentThread().getName(), taskMessage.getTaskId());
                    aiTaskWorker.handleTask(taskMessage);
                }
            });
            registrar.registerEndpoint(endpoint);
        }

    }

}
