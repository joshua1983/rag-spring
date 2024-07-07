package com.joshua.ollamaspring;

import com.joshua.ollamaspring.ai.AiConfig;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OllamaConfig {

   private final AiConfig aiConfig;

   public OllamaConfig(AiConfig aiConfig) {
      this.aiConfig = aiConfig;
   }

   @Bean
   public ChatLanguageModel chatLanguageModel() {
      return aiConfig.getModel();
   }
   @Bean
   public StreamingChatLanguageModel streamingChatLanguageModel(){
      return aiConfig.streamingChatLanguageModel();
   }
}
