package org.mcs.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.mcs.authservice.service.AuthenticationService;
import org.mcs.authservice.service.UserServiceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserServiceController {

    private final AuthenticationService authenticationService;

    @GetMapping("/auth/user/update")
    public void updateInfoByUser(){
        authenticationService.updateInfoByUser();
    }

}
