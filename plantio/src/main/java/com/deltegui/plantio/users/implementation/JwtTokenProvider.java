package com.deltegui.plantio.users.implementation;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.deltegui.plantio.users.application.TokenProvider;
import com.deltegui.plantio.users.domain.Token;
import com.deltegui.plantio.users.domain.User;
import org.springframework.core.env.Environment;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

public class JwtTokenProvider implements TokenProvider {
    private final byte[] privateKey;
    private final static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

    public JwtTokenProvider(Environment env) {
        assert env != null;
        String key = env.getProperty("jwttoken.privateKey");
        if (key == null) {
            throw new RuntimeException("Undefined private key for JwtTokenProvider in application properties");
        }
        this.privateKey = key.getBytes();
    }

    public JwtTokenProvider(String key) {
        assert key != null;
        assert key.length() > 0;
        this.privateKey = key.getBytes();
    }

    @Override
    public Token generateToken(User user) {
        assert user != null;
        var expiration = LocalDateTime.now().plusDays(1);
        var value = JWT.create()
                .withExpiresAt(localDateTimeToDate(expiration))
                .withClaim("name", user.getName())
                .withClaim("expiration", formatter.format(expiration))
                .sign(Algorithm.HMAC512(privateKey));
        return new Token(value, expiration, user.getName());
    }

    private Date localDateTimeToDate(LocalDateTime localDateTime) {
        var instant = localDateTime.toLocalDate()
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant();
        return Date.from(instant);
    }

    public Optional<Token> extractPayload(String token) {
        assert token != null;
        assert token.length() > 0;
        try {
            return Optional.of(this.tokenToPayload(token));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private Token tokenToPayload(String token) {
        var decoded = JWT.decode(token);
        var time = LocalDateTime.parse(decoded.getClaim("expiration").asString(), formatter);
        return new Token(token, time, decoded.getClaim("name").asString());
    }
}

