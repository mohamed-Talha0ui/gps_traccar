package com.tracker.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.stream.Collectors;

public class Token {

    public static String generateToken() {
        Algorithm algorithm = Algorithm.HMAC256("n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7w!z%C*F-J@NcRf".getBytes());
        String accessToken = JWT.create()
                .withSubject("serverSocket")
                .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .sign(algorithm);
        return "Bearer " + accessToken;
    }
}