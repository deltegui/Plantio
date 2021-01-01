package com.deltegui.plantio.store.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import com.deltegui.plantio.store.domain.StoreItem;
import org.springframework.stereotype.Service;

@Service
public class AdjustPriceCase implements UseCase<Void, Void> {
    private final StoreRepository storeRepository;

    public AdjustPriceCase(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    public Void handle(Void request) throws DomainException {
        this.storeRepository.getAll()
                .stream()
                .peek(StoreItem::adjustPrice)
                .forEach(this.storeRepository::update);
        return null;
    }
}
