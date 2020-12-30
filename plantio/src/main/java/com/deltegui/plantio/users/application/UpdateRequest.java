package com.deltegui.plantio.users.application;

import com.deltegui.plantio.users.domain.BagItem;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Set;

public final class UpdateRequest {
    private final @Length(max = 255, min = 2) @NotNull String name;
    private final @NotNull Set<BagItem> bag;

    public UpdateRequest(String name, Set<BagItem> bag) {
        this.name = name;
        this.bag = bag;
    }

    public String getName() {
        return name;
    }

    public Set<BagItem> getBag() {
        return bag;
    }
}
