package com.deltegui.plantio.store.implementation;

import com.deltegui.plantio.game.domain.PlantType;
import com.deltegui.plantio.store.application.StoreRepository;
import com.deltegui.plantio.store.domain.StoreItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MysqlStoreRepository implements StoreRepository {
    private final JdbcTemplate jdbcTemplate;

    public MysqlStoreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<StoreItem> getAll() {
        return this.jdbcTemplate.query(
                "select item, amount, old_amount, price from store",
                this::parseStoreItem);
    }

    @Override
    public StoreItem getByItem(PlantType itemType) {
        var itemList = this.jdbcTemplate.query(
                "select item, amount, old_amount, price from store where item = ?",
                this::parseStoreItem,
                itemType.name()
        );
        if (itemList.size() <= 0) {
            throw new RuntimeException("Expected to have an item of type " + itemType.name() + " but result has size of zero");
        }
        return itemList.get(0);
    }

    private StoreItem parseStoreItem(ResultSet rs, int number) throws SQLException {
        return new StoreItem(
                PlantType.fromString(rs.getNString("item")),
                rs.getInt("amount"),
                rs.getInt("old_amount"),
                rs.getDouble("price")
        );
    }

    @Override
    public void update(StoreItem item) {
        this.jdbcTemplate.update(
                "update store set amount = ?, price = ?, old_amount = ? where item = ?",
                item.getAmount(),
                item.getPrice(),
                item.getOldAmount(),
                item.getName()
        );
    }

    @Override
    public void add(StoreItem item) {
        this.jdbcTemplate.update(
                "insert into store (amount, old_amount, price, item) values (?, ?, ?, ?)",
                item.getAmount(),
                item.getOldAmount(),
                item.getPrice(),
                item.getName()
        );
    }
}
