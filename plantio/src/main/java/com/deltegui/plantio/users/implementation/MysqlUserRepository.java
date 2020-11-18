package com.deltegui.plantio.users.implementation;

import com.deltegui.plantio.users.application.UserRepository;
import com.deltegui.plantio.users.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class MysqlUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public MysqlUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findByName(String name) {
        var userList = this.jdbcTemplate.query(
                "select name, password from users where name = ?",
                (resultSet, number) ->
                        new User(resultSet.getNString("name"), resultSet.getNString("password")),
                name);
        return userList.isEmpty() ? Optional.empty() : Optional.of(userList.get(0));
    }

    @Override
    public void delete(User user) {
        this.jdbcTemplate.update(
                "delete from users where name = ?",
                user.getName());
    }

    @Override
    public void save(User user) {
        this.jdbcTemplate.update(
                "insert into users (name, password) values(?, ?)",
                user.getName(),
                user.getPassword());
    }
}
