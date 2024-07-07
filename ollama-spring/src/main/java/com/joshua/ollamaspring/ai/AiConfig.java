package com.joshua.ollamaspring.ai;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class AiConfig {
   static ChatMemory chatMemory;
   static ChatLanguageModel model;
   static EmbeddingModel embeddingModel;
   static EmbeddingStore embeddingStore;

   @Value("${langchain4j.ollama.chat-model.base-url}")
   private String baseUrl;
   @Value("${langchain4j.ollama.chat-model.model-name}")
   private String modelName;

   public ChatMemory getChatMemory() {
      if (chatMemory == null) {
         chatMemory = MessageWindowChatMemory.withMaxMessages(10);
         return chatMemory;
      }
      return chatMemory;
   }

   public ChatLanguageModel getModel() {
      if (model == null) {
         model = OllamaChatModel.builder()
            .modelName(modelName)
            .baseUrl(baseUrl)
            .build();
         return model;
      }
      return model;
   }

   public EmbeddingModel getEmbeddingModel() {
      if (embeddingModel == null) {
         embeddingModel = new AllMiniLmL6V2EmbeddingModel();
         return embeddingModel;
      }
      return embeddingModel;
   }

   public EmbeddingStore<TextSegment> getEmbeddingStore() {
      if (embeddingStore == null) {
         embeddingStore = new InMemoryEmbeddingStore<>();
         return embeddingStore;
      }
      return embeddingStore;
   }

   public StreamingChatLanguageModel streamingChatLanguageModel(){
      return OllamaStreamingChatModel.builder()
         .baseUrl(baseUrl)
         .modelName(modelName)
         .build();
   }
}
