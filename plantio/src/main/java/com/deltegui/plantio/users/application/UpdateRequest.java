package com.deltegui.plantio.users.application;

import com.deltegui.plantio.users.domain.BagItem;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

public final class UpdateRequest {
    private final @Length(max = 255, min = 2) @NotNull String name;
    private final @NotNull List<BagItem> bag;

    public UpdateRequest(String name, List<BagItem> bag) {
        this.name = name;
        this.bag = bag;
    }

    public String getName() {
        return name;
    }

    public List<BagItem> getBag() {
        return bag;
    }
}
