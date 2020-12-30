package com.deltegui.plantio.users.implementation;

import com.deltegui.plantio.users.application.*;
import com.deltegui.plantio.users.domain.BagItem;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final LoginCase loginCase;
    private final RegisterCase registerCase;
    private final UpdateCase updateCase;

    public UserController(LoginCase loginCase, RegisterCase registerCase, UpdateCase updateCase) {
        this.loginCase = loginCase;
        this.registerCase = registerCase;
        this.updateCase = updateCase;
    }

    @PostMapping("/login")
    public SessionResponse login(@Valid @RequestBody SessionRequest request) {
        return this.loginCase.handle(request);
    }

    @PostMapping("/register")
    public SessionResponse register(@Valid @RequestBody SessionRequest request) {
        return this.registerCase.handle(request);
    }

    @PostMapping("/update")
    public UpdateResponse update(Principal user, @Valid @RequestBody @NotNull Set<BagItem> bag) {
        return this.updateCase.handle(new UpdateRequest(user.getName(), bag));
    }
}
