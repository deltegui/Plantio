package com.deltegui.plantio.users.implementation;

import com.deltegui.plantio.game.domain.PlantType;
import com.deltegui.plantio.users.application.UserRepository;
import com.deltegui.plantio.users.domain.Seeds;
import com.deltegui.plantio.users.domain.User;
import com.deltegui.plantio.weather.domain.Coordinate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class MysqlUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public MysqlUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findByName(String name) {
        var userList = this.jdbcTemplate.query(
                "select name, password, latitude, longitude, money from users where name = ?",
                this::parseUserFromQueryResult,
                name);
        return userList.isEmpty() ? Optional.empty() : Optional.of(userList.get(0));
    }

    @Transactional
    @Override
    public void delete(User user) {
        this.jdbcTemplate.update(
                "delete from users where name = ?",
                user.getName());
    }

    @Transactional
    @Override
    public void save(User user) {
        user.getLastPosition().ifPresentOrElse(
                (lastPos) -> this.jdbcTemplate.update(
                        "insert into users (name, password, latitude, longitude, money) values (?, ?, ?, ?)",
                        user.getName(),
                        user.getPassword(),
                        lastPos.getLatitude(),
                        lastPos.getLongitude(),
                        user.getMoney()
                ),
                () -> this.jdbcTemplate.update(
                        "insert into users (name, password, money) values(?, ?, ?)",
                        user.getName(),
                        user.getPassword(),
                        user.getMoney()
                )
        );
        this.replaceBag(user);
    }

    @Transactional
    @Override
    public void update(User user) {
        user.getLastPosition().ifPresentOrElse(
                (lastPos) -> this.jdbcTemplate.update(
                        "update users set password = ?, latitude = ?, longitude = ?, money = ? where name = ?",
                        user.getPassword(),
                        lastPos.getLatitude(),
                        lastPos.getLongitude(),
                        user.getMoney(),
                        user.getName()
                ),
                () -> this.jdbcTemplate.update(
                        "update users set password = ?, money = ? where name = ?",
                        user.getPassword(),
                        user.getMoney(),
                        user.getName()
                )
        );
        this.replaceBag(user);
    }

    @Transactional
    @Override
    public List<User> getAll() {
        return this.jdbcTemplate.query(
                "select name, password, latitude, longitude, money from users",
                this::parseUserFromQueryResult);
    }

    private User parseUserFromQueryResult(ResultSet resultSet, int number) throws SQLException {
        var user = this.readUserWithoutBag(resultSet);
        var bag = this.loadBag(user);
        user.setBag(bag);
        return user;
    }

    private User readUserWithoutBag(ResultSet resultSet) throws SQLException {
        String name = resultSet.getNString("name");
        double money = resultSet.getDouble("money");
        String password = resultSet.getNString("password");
        Object latitude = resultSet.getObject("latitude");
        Object longitude = resultSet.getObject("longitude");
        if (latitude != null && longitude != null) {
            return new User(
                    name,
                    password,
                    new Coordinate((Double)latitude, (Double)longitude),
                    money
            );
        }
        return new User(
                name,
                password,
                money
        );
    }

    private void replaceBag(User user) {
        this.jdbcTemplate.update("delete from user_bag where owner = ?", user.getName());
        for (Seeds bagItem : user.getBag()) {
            this.jdbcTemplate.update(
                    "insert into user_bag (owner, item, amount) values(?, ?, ?)",
                    user.getName(),
                    bagItem.getItem(),
                    bagItem.getAmount()
            );
        }
    }

    private Set<Seeds> loadBag(User user) {
        return new HashSet<>(this.jdbcTemplate.query(
                "select item, amount from user_bag where owner = ?",
                (resultSet, number) -> new Seeds(PlantType.fromString(resultSet.getNString("item")), resultSet.getInt("amount")),
                user.getName()));
    }
}
