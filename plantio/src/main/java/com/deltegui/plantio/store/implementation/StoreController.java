package com.deltegui.plantio.store.implementation;

import com.deltegui.plantio.game.domain.PlantType;
import com.deltegui.plantio.store.application.*;
import com.deltegui.plantio.store.domain.Order;
import com.deltegui.plantio.store.domain.StoreItem;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/store")
public final class StoreController {
    private final BuyCase buyCase;
    private final SellCase sellCase;
    private final StoreRepository storeRepository;

    public StoreController(BuyCase buyCase, SellCase sellCase, StoreRepository storeRepository) {
        this.buyCase = buyCase;
        this.sellCase = sellCase;
        this.storeRepository = storeRepository;
    }

    @PostMapping("/buy/{item}/{amount}")
    public TransactionResult buy(Principal user, @PathVariable("item") String item, @PathVariable("amount") int amount) {
        return this.buyCase.handle(new TransactionRequest(
                user.getName(),
                PlantType.fromString(item),
                amount
        ));
    }

    @PostMapping("sell/{item}/{amount}")
    public TransactionResult sell(Principal user, @PathVariable("item") String item, @PathVariable("amount") int amount) {
        return this.sellCase.handle(new TransactionRequest(
                user.getName(),
                PlantType.fromString(item),
                amount
        ));
    }

    @GetMapping()
    public List<StoreItem> getAll() {
        return this.storeRepository.getAll();
    }
}
