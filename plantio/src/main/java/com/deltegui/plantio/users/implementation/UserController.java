package com.deltegui.plantio.users.implementation;

import com.deltegui.plantio.users.application.LoginCase;
import com.deltegui.plantio.users.application.RegisterCase;
import com.deltegui.plantio.users.application.SessionRequest;
import com.deltegui.plantio.users.application.SessionResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {
    private final LoginCase loginCase;
    private final RegisterCase registerCase;

    public UserController(LoginCase loginCase, RegisterCase registerCase) {
        this.loginCase = loginCase;
        this.registerCase = registerCase;
    }

    @PostMapping("/login")
    public SessionResponse login(@Valid @RequestBody SessionRequest request) {
        return this.loginCase.handle(request);
    }

    @PostMapping("/register")
    public SessionResponse register(@Valid @RequestBody SessionRequest request) {
        return this.registerCase.handle(request);
    }
}
