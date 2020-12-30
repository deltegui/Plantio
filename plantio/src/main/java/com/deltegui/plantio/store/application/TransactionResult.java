package com.deltegui.plantio.store.application;

import com.deltegui.plantio.store.domain.Order;
import com.deltegui.plantio.users.application.UpdateResponse;
import com.deltegui.plantio.users.domain.User;

public class TransactionResult {
    private final UpdateResponse user;
    private final Order order;

    public TransactionResult(User user, Order order) {
        this.user = new UpdateResponse(user);
        this.order = order;
    }

    public UpdateResponse getUser() {
        return user;
    }

    public Order getOrder() {
        return order;
    }
}
