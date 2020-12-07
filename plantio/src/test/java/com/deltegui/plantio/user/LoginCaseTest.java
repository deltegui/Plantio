package com.deltegui.plantio.user;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.users.application.*;
import com.deltegui.plantio.users.domain.Token;
import com.deltegui.plantio.users.domain.User;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class LoginCaseTest {
    private UserRepository userRepo;
    private PasswordHasher hasher;
    private TokenProvider provider;

    private final User user = new User("manolo", "manolopass");
    private final Token token = new Token("tokenizeme", LocalDateTime.now(), user.getName());
    private final SessionRequest request = new SessionRequest(user.getName(), user.getPassword());

    @BeforeEach
    public void initialize() {
        userRepo = mock(UserRepository.class);
        hasher = mock(PasswordHasher.class);
        provider = mock(TokenProvider.class);
    }

    @Test
    public void shouldLoginUserIfExists() {
        when(userRepo.findByName(anyString())).thenReturn(Optional.of(user));
        when(hasher.matches(anyString(), anyString())).thenReturn(true);
        when(provider.generateToken(any())).thenReturn(token);

        var response =  new LoginCase(userRepo, hasher, provider)
                .handle(request);

        assertEquals(response.getName(), user.getName());
        assertEquals(response.getToken(), token);
    }

    @Test
    public void shouldFailIfPasswordIsInvalid() {
        when(userRepo.findByName(anyString())).thenReturn(Optional.of(user));
        when(hasher.matches(anyString(), anyString())).thenReturn(false);
        DomainException ex = assertThrows(DomainException.class, () -> {
            var loginCase = new LoginCase(userRepo, hasher, provider);
            loginCase.handle(request);
        });
        assertEquals(ex.getError(), UserErrors.InvalidPassword);
    }

    @Test
    public void shouldFailIfUserDontExist() {
        when(userRepo.findByName(anyString())).thenReturn(Optional.empty());
        DomainException ex = assertThrows(DomainException.class, () -> {
            var loginCase = new LoginCase(userRepo, hasher, provider);
            loginCase.handle(request);
        });
        assertEquals(ex.getError(), UserErrors.NotFound);
    }
}
