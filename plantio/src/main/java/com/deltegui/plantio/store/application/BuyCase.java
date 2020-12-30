package com.deltegui.plantio.store.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import com.deltegui.plantio.store.domain.Order;
import com.deltegui.plantio.users.application.UserErrors;
import com.deltegui.plantio.users.application.UserRepository;
import org.springframework.stereotype.Service;

@Service
public final class BuyCase implements UseCase<TransactionRequest, Order> {
    private final Store store;
    private final UserRepository userRepository;

    public BuyCase(Store store, UserRepository userRepository) {
        this.store = store;
        this.userRepository = userRepository;
    }

    @Override
    public Order handle(TransactionRequest request) throws DomainException {
        var user = this.userRepository.findByName(request.getUser())
                .orElseThrow(() -> DomainException.fromError(UserErrors.NotFound));
        return this.store.buy(user, request.getSeeds());
    }
}
