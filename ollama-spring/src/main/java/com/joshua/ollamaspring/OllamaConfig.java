package com.joshua.ollamaspring;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaConfig {
   @Bean
   public ChatLanguageModel chatLanguageModel() {
      return OllamaChatModel.builder()
         .baseUrl("http://localhost:11434")
         .modelName("llama3")
         .build();
   }
   @Bean
   public StreamingChatLanguageModel streamingChatLanguageModel(){
      return OllamaStreamingChatModel.builder()
         .baseUrl("http://localhost:11434")
         .modelName("llama3")
         .build();
   }
}
