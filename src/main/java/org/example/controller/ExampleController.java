package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.service.ExampleService;
import org.example.dto.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statemachine")
@Slf4j
public class ExampleController {
    @Autowired
    private ExampleService service;


    @PutMapping("/sendevent")
    ResponseData updateStateMachine() {
        return service.updateStateMachine(1L);
    }

}
