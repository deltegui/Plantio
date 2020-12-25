package com.deltegui.plantio.users.application;

import com.deltegui.plantio.users.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByName(String name);
    void delete(User user);
    void save(User user);
    void update(User user);
    List<User> getAll();
}
