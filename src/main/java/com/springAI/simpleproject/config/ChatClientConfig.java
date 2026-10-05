package com.springAI.simpleproject.config;

import com.springAI.simpleproject.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.chat.client.ChatClient;
/*import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;*/
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder){

        var options = AnthropicChatOptions.builder().model("claude-sonnet-4-5").temperature(0.8)
                .maxTokens(100);
       return chatClientBuilder
               //.defaultOptions(options)
               /*.defaultAdvisors(new SimpleLoggerAdvisor())
               .defaultAdvisors(new TokenUsageAuditAdvisor())*/
               // Or passing advisors objects as a list , so all the advisors will be executed.
               .defaultAdvisors(List.of(new SimpleLoggerAdvisor(),
                               new TokenUsageAuditAdvisor()))
                .defaultSystem(
                        """
                        You are a professional HR assistant.\s 
                        Help employees and managers with questions about company policies,\s 
                        benefits, leave, onboarding, performance reviews, and general HR procedures.\s 
                        Be clear, empathetic, and maintain confidentiality.\s
                        Redirect legal or sensitive matters to the appropriate professional.\s
                        """
                ) // usage of the default system method
                .defaultUser("How can you help me ?")
                .build();
    }

    //For anthropic chat client
    @Bean
    public ChatClient anthropicAIChatClient(AnthropicChatModel anthropicChatModel){
        return ChatClient.create(anthropicChatModel);
    }

    //For ollama chat client
   /* @Bean
    public ChatClient ollamaAIChatClient(OllamaChatModel ollamaChatModel){
        ChatClient.Builder chatClientBuilder = ChatClient.builder(ollamaChatModel);
        return chatClientBuilder.build();
    }*/
}
