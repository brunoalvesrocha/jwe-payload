package com.example.demojwe;

import com.google.gson.Gson;
import org.jose4j.lang.JoseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoJweApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoJweApplication.class, args);
    }
}

@RestController
class EncryptionTestController {

    private final JWE jwe;

    EncryptionTestController(JWE jwe) {
        this.jwe = jwe;
    }

    @GetMapping("/receive-message")
    public ResponseEntity<?> sendMessage() throws JoseException {
        Info info = new Info().createInfo();
        String payload = toJson(info);
        String compactPayload = jwe.compactPayload(payload);
        return ResponseEntity.ok(compactPayload);
    }

    @PostMapping("unlocked-message")
    public ResponseEntity<?> receiveUnlockedMessage(@RequestBody String payloadLocked) throws JoseException {
        String payload = jwe.descompactPayload(payloadLocked);
        Info info = fromJson(payload);
        return ResponseEntity.ok(info);
    }

    @PostMapping("/send-message")
    public ResponseEntity<?> postMessage(@RequestBody Info info) throws JoseException {
        String parsePayload = toJson(info);
        String compactPayload = jwe.compactPayload(parsePayload);
        return ResponseEntity.ok(compactPayload);
    }



    private Info fromJson(String payload) {
        return new Gson().fromJson(payload, Info.class);
    }

    private String toJson(Object payload) {
        return new Gson().toJson(payload);
    }

}


