package org.mcs.userv1service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BcryptPasswordEncoderConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoderConfig() {
        return new BCryptPasswordEncoder();
    }
}
