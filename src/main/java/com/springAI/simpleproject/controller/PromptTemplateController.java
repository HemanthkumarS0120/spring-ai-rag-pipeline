package com.springAI.simpleproject.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PromptTemplateController {

    private final ChatClient chatClient;

    public PromptTemplateController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    //Using the below String from the userPromptTemplate.st file under resources/promptTemplates folder
    @Value("classpath:/promptTemplates/userPromptTemplate.st")
    Resource userPromptTemplate;

    /*String promptTemplate =
            """
                    A Customer named {customerName} sent the following message :
                    "{customerMessage} 
                    
                    Write a polite and helpful email response addressing the issue. maintain a 
                     professional tone and provide reassurance.
                     
                    Respond as if you're writing the email body   , Dont Include subject , signature""";*/


    @GetMapping("/email")
    public String emailResponse(@RequestParam("customerName") String customerName,
                       @RequestParam("customerMessage") String customerMessage) {
        String response = chatClient
                .prompt()
                .system("""
                        You are a professional customer service assistant which helps drafting email responses\s
                        to improve the productivity of the customer support team""")
                .user(promptUserSpec ->
                        promptUserSpec.text(userPromptTemplate)
                                .param("customerName",customerName)
                                .param("customerMessage",customerMessage))
                .call().content();

        return response;
    }

}
