package com.example.warehouse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainWindowController {

    @GetMapping(path = "/main")
    public String helloWorld(){
        return "Hello World";
    }
}
