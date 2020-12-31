package com.deltegui.plantio.store.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import com.deltegui.plantio.users.application.UserErrors;
import com.deltegui.plantio.users.application.UserRepository;
import org.springframework.stereotype.Service;

@Service
public final class SellCase implements UseCase<TransactionRequest, TransactionResult> {
    private final Store store;
    private final UserRepository userRepository;

    public SellCase(Store store, UserRepository userRepository) {
        this.store = store;
        this.userRepository = userRepository;
    }

    @Override
    public TransactionResult handle(TransactionRequest request) throws DomainException {
        var user = this.userRepository.findByName(request.getUser())
                .orElseThrow(() -> DomainException.fromError(UserErrors.NotFound));
        var result = this.store.sell(user, request.getSeeds());
        this.userRepository.update(user);
        return result;
    }
}
