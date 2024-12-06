package com.boukyApps.myguep.controller;

import com.boukyApps.myguep.service.OpenAIService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class DemoController {

  private final OpenAIService openAIService;
  public DemoController(OpenAIService openAIService) {
    this.openAIService = openAIService;
  }
  @GetMapping
  public ResponseEntity<String> sayHello() {
    return ResponseEntity.ok("Hello from secured endpoint");
  }

  @GetMapping("/openai")
  public ResponseEntity<String> testOpenAi(/*@RequestBody String prompt*/) {
    return ResponseEntity.ok(openAIService.callOpenAI("bonjour, cmment ça va ?"));

  }

}
