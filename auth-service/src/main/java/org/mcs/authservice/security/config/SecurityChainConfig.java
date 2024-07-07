package org.mcs.authservice.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@RequiredArgsConstructor
public class SecurityChainConfig {

    private final JwtAuthenticationConfigurer jwtAuthenticationConfigurer;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtAuthenticationConfigurer jwtAuthenticationConfigurer)throws Exception{

        httpSecurity.apply(jwtAuthenticationConfigurer);

        return httpSecurity
//                .csrf(Customizer.withDefaults())
                .cors(CorsConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/token/create", HttpMethod.POST.name()).permitAll()
                        .requestMatchers("/user/update", HttpMethod.GET.name()).permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .build();
    }
}
