package com.belatrix.demo.controller;

import com.belatrix.demo.domain.Message;
import com.belatrix.demo.service.LabService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/id")
@RequiredArgsConstructor
public class LabController {

    private final LabService labSservice;

    @GetMapping("/{id}")
    public ResponseEntity<Message> publish(@PathVariable(name = "id") String id,@RequestParam(value = "service",required = false) String service) {
//        Message message = service.publishSNS(id);
//        Message message = labSservice.publishKinesis(id);
        Message message = labSservice.sendMessage(service,id);
        return ResponseEntity.ok().body(message);
    }
}
