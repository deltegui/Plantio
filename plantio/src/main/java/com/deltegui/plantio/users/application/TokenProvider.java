package com.deltegui.plantio.users.application;

import com.deltegui.plantio.users.domain.*;

import java.util.Optional;

public interface TokenProvider {
    Token generateToken(User user);
    Optional<Token> extractPayload(String token);
}

