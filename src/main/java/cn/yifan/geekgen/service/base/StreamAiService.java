package cn.yifan.geekgen.service.base;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.TokenStream;
import dev.langchain4j.service.UserMessage;

/**
 * @FileName AgentService
 * @Description
 * @Author yifan
 * @date 2025-02-26 12:58
 **/

public interface StreamAiService {

    @SystemMessage(fromResource = "prompt/english-helper-prompt.txt")
    public TokenStream chat(@MemoryId Long userId, @UserMessage String question);

}
