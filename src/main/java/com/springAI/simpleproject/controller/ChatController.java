package com.springAI.simpleproject.controller;

import com.springAI.simpleproject.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    // For Single chat model
    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient=chatClient;
        /*this.chatClient = chatClientBuilder
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
                .build();*/
    }


    /*The message we are providing in the below Get method of the controller , will be overridden by the above
    default system or user messages, if anything is not provided below then the above default messages will be used as a fallback.*/
    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message){
        String response = chatClient
                .prompt()
                //.advisors(new TokenUsageAuditAdvisor())
                /*.system("""
                        You are a professional HR assistant.\s 
                        Help employees and managers with questions about company policies,\s 
                        benefits, leave, onboarding, performance reviews, and general HR procedures.\s 
                        Be clear, empathetic, and maintain confidentiality.\s
                        Redirect legal or sensitive matters to the appropriate professional.\s
                        """)*/  // System role message to the LLM
                 .user(message) // User message , we are sending the same which we received from the caller
                .call().content();
        return response;
    }


    // for Multiple chat models
     /*private final ChatClient anthropicAiChatClient;
     private final ChatClient ollamaChatClient;

    public ChatController(@Qualifier("anthropicAIChatClient") ChatClient anthropicAiChatClient,
                          @Qualifier("ollamaAIChatClient") ChatClient ollamaChatClient) {
        this.anthropicAiChatClient = anthropicAiChatClient;
        this.ollamaChatClient = ollamaChatClient;
    }


    //For multi chat model
    @GetMapping("/anthropic/chat")
    public String anthropicAIChat(@RequestParam("message") String message){
        return anthropicAiChatClient.prompt(message).call().content();
    }

    @GetMapping("/ollama/chat")
    public String ollamaAIChat(@RequestParam("message") String message){
        return ollamaChatClient.prompt(message).call().content();
    }*/
}
