package org.mcs.apigateway.token.model.deserializer;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mcs.apigateway.dto.RefreshToken;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenDeserializer implements Function<String, RefreshToken> {

    @Override
    public RefreshToken apply(String string) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(string);
            JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
            return RefreshToken.builder()
                    .id(UUID.fromString(jwtClaimsSet.getJWTID()))
                    .subject(jwtClaimsSet.getSubject())
                    .authorities(jwtClaimsSet.getStringListClaim("authorities"))
                    .createdDt(jwtClaimsSet.getIssueTime().toInstant())
                    .expiresDt(jwtClaimsSet.getExpirationTime().toInstant())
                    .build();
        }catch (ParseException exception){
            log.error("Can't parse RefreshToken: %s".formatted(exception));
        }
        return null;
    }

    //    @Override
//    public RefreshToken apply(String string) {
//        try{
//            MACVerifier macVerifier = new MACVerifier(refreshVerifyConfig.cryptSecret());
//
//            SignedJWT signedJWT = SignedJWT.parse(string);
//
//            if(signedJWT.verify(macVerifier)){
//
//                JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();
//
//                return RefreshToken.builder()
//                        .id(UUID.fromString(jwtClaimsSet.getJWTID()))
//                        .subject(jwtClaimsSet.getSubject())
//                        .createdDt(jwtClaimsSet.getIssueTime().toInstant())
//                        .expiresDt(jwtClaimsSet.getExpirationTime().toInstant())
//                        .build();
//            }
//        }catch (JOSEException | ParseException exception){
//            log.error("Can't parse RefreshToken: %s".formatted(exception));
//        }
//        return null;
//    }
}
