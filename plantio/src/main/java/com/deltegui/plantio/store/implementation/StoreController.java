package com.deltegui.plantio.store.implementation;

import com.deltegui.plantio.store.application.BuyCase;
import com.deltegui.plantio.store.application.SellCase;
import com.deltegui.plantio.store.application.TransactionRequest;
import com.deltegui.plantio.store.domain.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/store")
public final class StoreController {
    private final BuyCase buyCase;
    private final SellCase sellCase;

    public StoreController(BuyCase buyCase, SellCase sellCase) {
        this.buyCase = buyCase;
        this.sellCase = sellCase;
    }

    @PostMapping("/buy")
    public Order buy(@Valid @RequestBody TransactionRequest transaction) {
        return this.buyCase.handle(transaction);
    }

    @PostMapping("sell")
    public Order sell(@Valid @RequestBody TransactionRequest transaction) {
        return this.sellCase.handle(transaction);
    }
}
