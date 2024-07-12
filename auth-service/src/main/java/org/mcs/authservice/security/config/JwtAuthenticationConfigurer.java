package org.mcs.authservice.security.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mcs.authservice.security.service.JwtAuthenticationService;
import org.mcs.authservice.security.service.UserEntityDetailsService;
import org.mcs.authservice.token.deserializer.AccessTokenDeserializer;
import org.mcs.authservice.token.deserializer.RefreshTokenDeserializer;
import org.mcs.authservice.token.factory.AccessTokenFactory;
import org.mcs.authservice.token.factory.RefreshTokenFactory;
import org.mcs.authservice.token.serializer.AccessTokenSerializer;
import org.mcs.authservice.token.serializer.RefreshTokenSerializer;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationConfigurer extends AbstractHttpConfigurer<JwtAuthenticationConfigurer, HttpSecurity> {

    private final AccessTokenDeserializer accessTokenDeserializer;

    private final RefreshTokenDeserializer refreshTokenDeserializer;

    private final UserEntityDetailsService userEntityDetailsService;


    @Override
    public void init(HttpSecurity builder) throws Exception {
        var csrfConfigurer = builder.getConfigurer(CsrfConfigurer.class);
        if(csrfConfigurer != null){
            csrfConfigurer.ignoringRequestMatchers((new AntPathRequestMatcher("/api/v1/auth/token/create", HttpMethod.POST.name())));
        }
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        var jwtAuthenticationFilter = new AuthenticationFilter(builder.getSharedObject(AuthenticationManager.class), new JwtAuthenticationService(accessTokenDeserializer, refreshTokenDeserializer));

        jwtAuthenticationFilter.setSuccessHandler(((request, response, authentication) -> {
            CsrfFilter.skipRequest(request);
        }));

        jwtAuthenticationFilter.setFailureHandler(((request, response, exception) -> {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
        }));

        var authenticationProvider = new PreAuthenticatedAuthenticationProvider();
        authenticationProvider.setPreAuthenticatedUserDetailsService(userEntityDetailsService);

        builder
                .addFilterBefore(jwtAuthenticationFilter, CsrfFilter.class)
                .authenticationProvider(authenticationProvider);
    }
}
