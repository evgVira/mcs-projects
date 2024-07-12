package org.mcs.authservice.service;

import lombok.RequiredArgsConstructor;
import org.mcs.authservice.token.deserializer.AccessTokenDeserializer;
import org.mcs.authservice.token.deserializer.RefreshTokenDeserializer;
import org.mcs.authservice.token.model.AccessToken;
import org.mcs.authservice.token.model.RefreshToken;
import org.mcs.authservice.token.model.Token;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenValidationService {

    private final AccessTokenDeserializer accessTokenDeserializer;

    private final RefreshTokenDeserializer refreshTokenDeserializer;

//    public Token validationTokenFromApiGatewayService(String requestToken){
//        if(requestToken != null){
//            AccessToken accessToken = accessTokenDeserializer.apply(requestToken);
//            if(accessToken != null){
//                return accessToken;
//            }
//            RefreshToken refreshToken = refreshTokenDeserializer.apply(requestToken);
//            if(refreshToken != null){
//                return refreshToken;
//            }
//        }
//        return null;
//    }

    public AccessToken validationAccessToken(String requestToken){
        if(requestToken != null){
            AccessToken accessToken = accessTokenDeserializer.apply(requestToken);
            if(accessToken != null){
                return accessToken;
            }
        }
        return null;
    }
}
