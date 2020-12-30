package com.deltegui.plantio.store.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.store.domain.Order;
import com.deltegui.plantio.store.domain.Seeds;
import com.deltegui.plantio.users.domain.User;
import org.springframework.stereotype.Service;

@Service
public class Store {
    private final StoreRepository storeRepository;

    public Store(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public synchronized TransactionResult buy(User user, Seeds seeds) throws DomainException {
        var storeItem = storeRepository.getByItem(seeds.getType());
        var order = new Order(storeItem, seeds.getAmount());
        if (! user.canPay(order)) {
            throw DomainException.fromError(StoreErrors.UNAFFORDABLE);
        }
        if (storeItem.notHaveStock(seeds.getAmount())) {
            throw DomainException.fromError(StoreErrors.OUT_OF_STOCK);
        }
        user.payFor(order);
        storeItem.substract(seeds.getAmount());
        this.storeRepository.update(storeItem);
        return new TransactionResult(user, order);
    }

    public synchronized TransactionResult sell(User user, Seeds seeds) throws DomainException {
        var storeItem = storeRepository.getByItem(seeds.getType());
        var order = new Order(storeItem, seeds.getAmount());
        if (! user.canSell(order)) {
            throw DomainException.fromError(StoreErrors.NOT_ENOUGH_ELEMENTS_TO_SELL);
        }
        user.sell(order);
        storeItem.add(seeds.getAmount());
        this.storeRepository.update(storeItem);
        return new TransactionResult(user, order);
    }
}
