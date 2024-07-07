package org.mcs.authservice.token.deserializer;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mcs.authservice.token.config.RefreshVerifyConfig;
import org.mcs.authservice.token.model.RefreshToken;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
@Slf4j
@Qualifier("refreshConfigBean")
public class RefreshTokenDeserializer implements Function<String, RefreshToken> {

    private final RefreshVerifyConfig refreshVerifyConfig;


    @Override
    public RefreshToken apply(String string) {
        try{

            MACVerifier macVerifier = new MACVerifier(refreshVerifyConfig.cryptSecret());

            SignedJWT signedJWT = SignedJWT.parse(string);

            if(signedJWT.verify(macVerifier)){

                JWTClaimsSet jwtClaimsSet = signedJWT.getJWTClaimsSet();

                return RefreshToken.builder()
                        .id(UUID.fromString(jwtClaimsSet.getJWTID()))
                        .subject(jwtClaimsSet.getSubject())
                        .authorities(jwtClaimsSet.getStringListClaim("authorities"))
                        .createdAt(jwtClaimsSet.getIssueTime().toInstant())
                        .expiresAt(jwtClaimsSet.getExpirationTime().toInstant())
                        .build();
            }
        }catch (JOSEException | ParseException exception){
            log.error("Can't parse RefreshToken: %s".formatted(exception));
        }
        return null;
    }
}
