package com.joshua.ollamaspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class OllamaSpringApplication {

   public static void main(String[] args) {

      SpringApplication.run(OllamaSpringApplication.class, args);
   }

}
