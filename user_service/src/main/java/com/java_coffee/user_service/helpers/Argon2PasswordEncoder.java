package com.java_coffee.user_service.helpers;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Argon2PasswordEncoder{

    // passed a quick test to generate salts, hash passwords, and verify 12/28/2023
    private final Random RANDOM = new SecureRandom();

    // ez pz lemon squeezy tuning knobs
    private final int DEFAULT_ITERATIONS = 2;
    private final int DEFAULT_MEMORY_LIMIT = 15000;
    private final int DEFAULT_HASH_LENGTH = 256;
    private final int DEFAULT_SALT_LENGTH = 64;
    private final int DEFAULT_PARALLELISM = 1;
    
    @Value("${secret.sauce}")
    private byte[] pepperyBytes;
    private byte[] saltyBytes;

    public String generateSalt() {
        byte[] salt = new byte[DEFAULT_SALT_LENGTH];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private Argon2Parameters.Builder argonBuilder = new Argon2Parameters.Builder(Argon2Parameters.ARGON2_id)
        .withVersion(Argon2Parameters.ARGON2_VERSION_13)
        .withIterations(DEFAULT_ITERATIONS)
        .withMemoryAsKB(DEFAULT_MEMORY_LIMIT)
        .withParallelism(DEFAULT_PARALLELISM)
        .withSalt(saltyBytes)
        .withSecret(pepperyBytes);

    private Argon2BytesGenerator argonGenerator = new Argon2BytesGenerator();
    
    public String hash(String password, String salt) {
        this.saltyBytes = salt.getBytes(StandardCharsets.UTF_8);
        argonGenerator.init(argonBuilder.build());
        byte[] result = new byte[DEFAULT_HASH_LENGTH];
        argonGenerator.generateBytes(password.getBytes(StandardCharsets.UTF_8), result, 0 , result.length);
        return Base64.getEncoder().encodeToString(result);
    }

    public boolean verify(String password, String salt, String hash) {
        boolean verfied = false;
        if (password != null && !password.isEmpty() && !password.isBlank()) {
            if (hash.equals(hash(password, salt))) {
                verfied = true;
            }
        }
        return verfied;
    }
    
}
