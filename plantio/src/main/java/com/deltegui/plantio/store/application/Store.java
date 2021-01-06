package com.deltegui.plantio.store.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.store.domain.Order;
import com.deltegui.plantio.store.domain.TransactionItem;
import com.deltegui.plantio.users.domain.User;
import org.springframework.stereotype.Service;

@Service
public class Store {
    private final StoreRepository storeRepository;

    public Store(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public synchronized TransactionResult buy(User user, TransactionItem item) throws DomainException {
        var storeItem = storeRepository.getByItem(item.getType());
        var order = new Order(storeItem, item.getAmount());
        if (! user.canPay(order)) {
            throw DomainException.fromError(StoreErrors.UNAFFORDABLE);
        }
        if (storeItem.notHaveStock(item.getAmount())) {
            throw DomainException.fromError(StoreErrors.OUT_OF_STOCK);
        }
        if (! item.canBeAppliedTo(user, order)) {
            throw DomainException.fromError(StoreErrors.CANT_BE_APPLIED);
        }
        user.payFor(order);
        storeItem.substract(item.getAmount());
        this.storeRepository.update(storeItem);
        return new TransactionResult(user, order);
    }

    public synchronized TransactionResult sell(User user, TransactionItem seeds) throws DomainException {
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
