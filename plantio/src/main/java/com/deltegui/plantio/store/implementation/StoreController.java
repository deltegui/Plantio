package com.deltegui.plantio.store.implementation;

import com.deltegui.plantio.game.domain.PlantType;
import com.deltegui.plantio.store.application.BuyCase;
import com.deltegui.plantio.store.application.SellCase;
import com.deltegui.plantio.store.application.TransactionRequest;
import com.deltegui.plantio.store.domain.Order;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/store")
public final class StoreController {
    private final BuyCase buyCase;
    private final SellCase sellCase;

    public StoreController(BuyCase buyCase, SellCase sellCase) {
        this.buyCase = buyCase;
        this.sellCase = sellCase;
    }

    @PostMapping("/buy/{item}/{amount}")
    public Order buy(Principal user, @PathVariable("item") String item, @PathVariable("amount") int amount) {
        return this.buyCase.handle(new TransactionRequest(
                user.getName(),
                PlantType.fromString(item),
                amount
        ));
    }

    @PostMapping("sell/{item}/{amount}")
    public Order sell(Principal user, @PathVariable("item") String item, @PathVariable("amount") int amount) {
        return this.sellCase.handle(new TransactionRequest(
                user.getName(),
                PlantType.fromString(item),
                amount
        ));
    }
}
