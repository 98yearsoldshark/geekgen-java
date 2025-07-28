package cn.yifan.geekgen.config;

import cn.yifan.geekgen.service.base.StreamAiService;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @FileName LangchainConfig
 * @Description
 * @Author yifan
 * @date 2025-01-30 15:33
 **/

@Configuration
public class LangchainConfig {

    @Bean
    public StreamAiService agentService(StreamingChatLanguageModel streamingChatLanguageModel) {
        return AiServices.builder(StreamAiService.class)
                .streamingChatLanguageModel(streamingChatLanguageModel)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.withMaxMessages(10))
                .build();
    }

}
