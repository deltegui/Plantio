package com.deltegui.plantio.store;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.game.domain.PlantType;
import com.deltegui.plantio.store.application.Store;
import com.deltegui.plantio.store.application.StoreErrors;
import com.deltegui.plantio.store.application.StoreRepository;
import com.deltegui.plantio.store.domain.Seeds;
import com.deltegui.plantio.store.domain.StoreItem;
import com.deltegui.plantio.users.domain.BagItem;
import com.deltegui.plantio.users.domain.User;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StoreTest {
    @Test
    public void ifUserCantPayForSeedsShouldThrowException() {
        var storeItem = createStoreItemWithAmountPrice(5, 2);
        var user = createUserWithMoney(1);
        var seeds = createSeeds(1);

        var storeRepo = mock(StoreRepository.class);
        when(storeRepo.getByItem(any())).thenReturn(storeItem);

        var store = new Store(storeRepo);

        var ex = assertThrows(DomainException.class, () -> store.buy(user, seeds));
        assertEquals(StoreErrors.UNAFFORDABLE, ex.getError());
    }

    @Test
    public void ifThereIsNoEnoughStockToBuyThrowsException() {
        var storeItem = createStoreItemWithAmountPrice(1, 2);
        var user = createUserWithMoney(10);
        var seeds = createSeeds(2);

        var storeRepo = mock(StoreRepository.class);
        when(storeRepo.getByItem(any())).thenReturn(storeItem);

        var store = new Store(storeRepo);

        var ex = assertThrows(DomainException.class, () -> store.buy(user, seeds));
        assertEquals(StoreErrors.OUT_OF_STOCK, ex.getError());
    }

    @Test
    public void shouldBuyItems() {
        var storeItem = createStoreItemWithAmountPrice(5, 2);
        var user = createUserWithMoney(10);
        var seeds = createSeeds(2);

        var storeRepo = mock(StoreRepository.class);
        when(storeRepo.getByItem(any())).thenReturn(storeItem);
        var store = new Store(storeRepo);

        store.buy(user, seeds);

        assertEquals(1, user.getBag().size());
        assertEquals(2, user.getBag().get(0).getAmount());
        assertEquals(6, user.getMoney());
        assertEquals(3, storeItem.getAmount());
    }

    @Test
    public void ifUserDontHaveEnoughElementsToSellThrowsException() {
        var storeItem = createStoreItemWithAmountPrice(1, 2);
        var user = createUserWithMoney(1);
        var seeds = createSeeds(3);

        var storeRepo = mock(StoreRepository.class);
        when(storeRepo.getByItem(any())).thenReturn(storeItem);

        var store = new Store(storeRepo);

        var ex = assertThrows(DomainException.class, () -> store.sell(user, seeds));
        assertEquals(StoreErrors.NOT_ENOUGH_ELEMENTS_TO_SELL, ex.getError());
    }

    @Test
    public void shouldSellItemsAndDeleteBagItemsIfAmountIsZero() {
        var storeItem = createStoreItemWithAmountPrice(1, 4);
        var user = createUserWithMoneyBag(1, createBagItem(5));
        var seeds = createSeeds(5);

        var storeRepo = mock(StoreRepository.class);
        when(storeRepo.getByItem(any())).thenReturn(storeItem);
        var store = new Store(storeRepo);

        store.sell(user, seeds);

        assertEquals(0, user.getBag().size());
        assertEquals(21, user.getMoney());
        assertEquals(6, storeItem.getAmount());
    }

    @Test
    public void shouldSellItems() {
        var storeItem = createStoreItemWithAmountPrice(1, 4);
        var user = createUserWithMoneyBag(1, createBagItem(8));
        var seeds = createSeeds(5);

        var storeRepo = mock(StoreRepository.class);
        when(storeRepo.getByItem(any())).thenReturn(storeItem);
        var store = new Store(storeRepo);

        store.sell(user, seeds);

        assertEquals(1, user.getBag().size());
        assertEquals(3, user.getBag().get(0).getAmount());
        assertEquals(21, user.getMoney());
        assertEquals(6, storeItem.getAmount());
    }

    private User createUserWithMoney(double money) {
        return new User("manolo", "manolo", money);
    }

    private User createUserWithMoneyBag(double money, BagItem... items) {
        var u = new User("manolo", "manolo", money);
        u.getBag().addAll(Arrays.asList(items));
        return u;
    }

    private StoreItem createStoreItemWithAmountPrice(int amount, double price) {
        return new StoreItem(PlantType.CACTUS, amount, price);
    }

    private Seeds createSeeds(int amount) {
        return new Seeds(PlantType.CACTUS, amount);
    }

    private BagItem createBagItem(int amount) {
        return new BagItem(PlantType.CACTUS, amount);
    }
}
