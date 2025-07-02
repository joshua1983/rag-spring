package com.joshua.ollamaspring.controller;

import com.joshua.ollamaspring.ai.AssistantService;
import com.joshua.ollamaspring.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class AssistantController {

   AssistantService assistant;

   @Autowired
   public AssistantController(AssistantService assistant) {
      this.assistant = assistant;
   }

   @PostMapping("/chat")
   public ResponseEntity chat(@RequestBody MessageDto requestMessage) {
      String message = requestMessage.message();
      String response = assistant.sendMessage(message);
      MessageDto responseMessage = new MessageDto(response);
      return ResponseEntity.ok(responseMessage);
   }

   @GetMapping("/load")
   public ResponseEntity loadDocument(){
      assistant.loadDocument();
      return ResponseEntity.ok().build();
   }
}
