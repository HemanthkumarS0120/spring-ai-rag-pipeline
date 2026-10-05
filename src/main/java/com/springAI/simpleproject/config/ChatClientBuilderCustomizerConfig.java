package com.springAI.simpleproject.config;

import com.springAI.simpleproject.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClientBuilderCustomizer;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientBuilderCustomizerConfig {

    @Bean
    public ChatClientBuilderCustomizer loggerAdvisor(){
        return chatClientBuilder -> chatClientBuilder.defaultAdvisors(new SimpleLoggerAdvisor());
    }

    @Bean
    @ConditionalOnProperty(name = "audit.token-usage.enabled",havingValue = "true")
    public ChatClientBuilderCustomizer auditAdvisor(){
        return chatClientBuilder -> chatClientBuilder.defaultAdvisors(new TokenUsageAuditAdvisor());
    }
}
