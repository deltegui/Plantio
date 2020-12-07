package com.deltegui.plantio.user;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.users.application.*;
import com.deltegui.plantio.users.domain.Token;
import com.deltegui.plantio.users.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RegisterCaseTest {
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
    public void shouldRegisterUserIfNotExists() {
        when(userRepo.findByName(anyString())).thenReturn(Optional.empty());
        when(hasher.hash(anyString())).thenReturn("hashed");
        when(provider.generateToken(any())).thenReturn(token);
        var registerCase = new RegisterCase(userRepo, hasher, provider);
        var response = registerCase.handle(request);
        Assertions.assertEquals(token, response.getToken());
        assertEquals(user.getName(), response.getName());
        verify(userRepo, times(1)).save(any());
    }

    @Test
    public void shouldFailIfUserAlreadyExists() {
        when(userRepo.findByName(anyString())).thenReturn(Optional.of(user));
        var registerCase = new RegisterCase(userRepo, hasher, provider);
        var ex = assertThrows(DomainException.class, () -> registerCase.handle(request));
        assertEquals(ex.getError(), UserErrors.AlreadyExsists);
    }
}
