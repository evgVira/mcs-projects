package org.mcs.authservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class TestController {

    @GetMapping("/test")
    public String testRequest(){
        return "this is authenticated entryPoint";
    }
}
