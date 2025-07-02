package com.joshua.ollamaspring.ai;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static dev.langchain4j.data.message.UserMessage.userMessage;

@Service
public class AssistantService {
   private final ResourceLoader resourceLoader;
   private final AiConfig aiConfig;

   public AssistantService(ResourceLoader resourceLoader, AiConfig config) {
      this.resourceLoader = resourceLoader;
      this.aiConfig = config;
   }


   public String sendMessage(String message) {

      ChatMemory chatMemory = aiConfig.getChatMemory();
      chatMemory.add(userMessage(message));
      List<String> embeddings = findEmbeddings(message);
      addContextToChat(embeddings);
      ChatLanguageModel model = aiConfig.getModel();
      AiMessage answer = model.generate(chatMemory.messages()).content();
      chatMemory.add(answer);
      return answer.text();
   }

   private void addContextToChat(List<String> context){
      ChatMemory chatMemory = aiConfig.getChatMemory();
      for (String s : context){
         chatMemory.add(userMessage(s));
      }
   }

   private List<String> findEmbeddings(String message) {
      EmbeddingModel embeddingModel = aiConfig.getEmbeddingModel();
      Embedding question = embeddingModel.embed(message).content();
      int maxResult = 3;
      double minScore = 0.7;
      EmbeddingStore embeddingStore = aiConfig.getEmbeddingStore();
      List<EmbeddingMatch<TextSegment>> relevant = embeddingStore.findRelevant(question, maxResult, minScore);
      return relevant.stream().map(r -> r.embedded().text()).toList();
   }

   public void loadDocument(){
      Document documento = FileSystemDocumentLoader.loadDocument(loadFilePath());
      DocumentSplitter splitter = DocumentSplitters.recursive(2000,200);
      List<TextSegment> segments = splitter.split(documento);
      EmbeddingModel embeddingModel = aiConfig.getEmbeddingModel();
      List<Embedding> embeddings = embeddingModel.embedAll(segments).content();
      EmbeddingStore<TextSegment> embeddingStore = aiConfig.getEmbeddingStore();
      embeddingStore.addAll(embeddings,segments);
   }

   private String loadFilePath(){
      Resource resource = resourceLoader.getResource("classpath:static/1706.03762v7.pdf");
      File file = null;
      try {
         file = resource.getFile();
      } catch (IOException e) {
         throw new RuntimeException(e);
      }
      return file.getAbsolutePath();
   }

}
