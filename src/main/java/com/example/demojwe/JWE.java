package com.example.demojwe;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwe.ContentEncryptionAlgorithmIdentifiers;
import org.jose4j.jwe.JsonWebEncryption;
import org.jose4j.jwe.KeyManagementAlgorithmIdentifiers;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author brunorocha
 */
@Component
public class JWE {

    @Value("${key.json.jwe}")
    private String jwkJson;

    public String compactPayload(String payload) throws JoseException {
        JsonWebKey jwk = JsonWebKey.Factory.newJwk(jwkJson);
        JsonWebEncryption senderJwe = new JsonWebEncryption();
        senderJwe.setPlaintext(payload);
        senderJwe.setAlgorithmHeaderValue(KeyManagementAlgorithmIdentifiers.DIRECT);
        senderJwe.setEncryptionMethodHeaderParameter(ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        senderJwe.setKey(jwk.getKey());
        String compactSerialization = senderJwe.getCompactSerialization();
        System.out.println("Compacted payload =>>>>>>> " + compactSerialization);
        return compactSerialization;
    }

    public String descompactPayload(String payload) throws JoseException {
        JsonWebKey jwk = JsonWebKey.Factory.newJwk(jwkJson);
        JsonWebEncryption receiverJwe = new JsonWebEncryption();
        AlgorithmConstraints algContrains = new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST, KeyManagementAlgorithmIdentifiers.DIRECT);
        receiverJwe.setAlgorithmConstraints(algContrains);
        AlgorithmConstraints encContraints = new AlgorithmConstraints(AlgorithmConstraints.ConstraintType.WHITELIST, ContentEncryptionAlgorithmIdentifiers.AES_128_CBC_HMAC_SHA_256);
        receiverJwe.setContentEncryptionAlgorithmConstraints(encContraints);
        receiverJwe.setCompactSerialization(payload);
        receiverJwe.setKey(jwk.getKey());
        System.out.println("Descompacted payload =>>>>>>> " + receiverJwe.getPlaintextString());
        return receiverJwe.getPlaintextString();
    }

}
