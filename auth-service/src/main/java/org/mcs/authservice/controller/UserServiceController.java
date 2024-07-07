package org.mcs.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.mcs.authservice.service.AuthenticationService;
import org.mcs.authservice.service.UserServiceClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserServiceController {

    private final AuthenticationService authenticationService;

    @GetMapping("/user/update")
    public void updateInfoByUser(){
        authenticationService.updateInfoByUser();
    }

}
