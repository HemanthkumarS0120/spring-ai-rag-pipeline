package com.springAI.simpleproject.controller;

import org.springframework.ai.anthropic.AnthropicChatModel;
import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.model.anthropic.autoconfigure.AnthropicChatAutoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PromptStuffingController {

    private final ChatClient chatClient;

    public PromptStuffingController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    //Using the below String from the userPromptTemplate.st file under resources/promptTemplates folder
    @Value("classpath:/promptTemplates/systemPromptTemplate.st")
    Resource systemPromptTemplate;

    /*String promptTemplate =
            """
                    A Customer named {customerName} sent the following message :
                    "{customerMessage} 
                    
                    Write a polite and helpful email response addressing the issue. maintain a 
                     professional tone and provide reassurance.
                     
                    Respond as if you're writing the email body   , Dont Include subject , signature""";*/


    @GetMapping("/prompt-stuffing")
    public String promptStuffing(@RequestParam("message") String message) {
        String response = chatClient
                .prompt()
                //.options(AnthropicChatOptions.builder().model(Anthr))
                // By commenting the below line of system , the flow has been fallback to the defaultsystem message inside the chatclient config class
                .system(systemPromptTemplate)
                .user(message)
                .call().content();

        return response;
    }

}
