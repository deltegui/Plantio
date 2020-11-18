package com.deltegui.plantio.users.application;

public interface PasswordHasher {
    String hash(String rawPassword);
    boolean matches(String hashed, String raw);
}
