package com.example.demojwe;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.OctetSequenceKey;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * @author brunorocha
 */
public class GenerateOctetHMAC {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        SecretKey hmacKey = KeyGenerator.getInstance("HmacSha256").generateKey();

// Convert to JWK format
        JWK jwk = new OctetSequenceKey.Builder(hmacKey)
                .keyID(UUID.randomUUID().toString()) // Give the key some ID (optional)
                .algorithm(JWSAlgorithm.HS256) // Indicate the intended key alg (optional)
                .build();

// Output
        System.out.println(jwk);
    }
}
