package com.springAI.simpleproject.config;

import com.springAI.simpleproject.advisors.TokenUsageAuditAdvisor;
import com.springAI.simpleproject.tools.TimeTools;
import org.springframework.ai.chat.client.ChatClient;
/*import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;*/
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.tool.execution.DefaultToolExecutionExceptionProcessor;
import org.springframework.ai.tool.execution.ToolExecutionExceptionProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class HelpDeskChatClientConfig {

    @Value("classpath:/promptTemplates/helpDeskSystemPromptTemplate.st")
    Resource systemPromptTemplate;

    @Bean("helpDeskChatClient")
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory,
                                 TimeTools timeTools){
        // As we have defined this in the chatclientbuilder customizer ( so here removing , because it is duplicated )
        // this can also be removed ion other chatclient config classes)
        /*Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor tokenAdvisor = new TokenUsageAuditAdvisor();*/
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

       return chatClientBuilder
               .defaultSystem(systemPromptTemplate)
               .defaultTools(timeTools)
               //.defaultAdvisors(List.of(loggerAdvisor,memoryAdvisor,tokenAdvisor))
               .defaultAdvisors(List.of(memoryAdvisor))
               .build();

    }


    // for throwing the exception directly to the client , instead of passing the exception to LLM model
    /*@Bean
    ToolExecutionExceptionProcessor toolExecutionExceptionProcessor(){
        return new DefaultToolExecutionExceptionProcessor(true);
    }*/

}
