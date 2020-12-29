package com.deltegui.plantio.users.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import com.deltegui.plantio.users.domain.User;
import org.springframework.stereotype.Service;

@Service
public final class RegisterCase implements UseCase<SessionRequest, SessionResponse> {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final TokenProvider tokenProvider;

    public RegisterCase(UserRepository userRepository, PasswordHasher hasher, TokenProvider provider) {
        this.userRepository = userRepository;
        this.passwordHasher = hasher;
        this.tokenProvider = provider;
    }

    @Override
    public SessionResponse handle(SessionRequest request) throws DomainException {
        userRepository.findByName(request.getName()).ifPresent(u -> {
            throw DomainException.fromError(UserErrors.AlreadyExsists);
        });
        var hashed = passwordHasher.hash(request.getPassword());
        var user = new User(request.getName(), hashed);
        userRepository.save(user);
        var token = tokenProvider.generateToken(user);
        return new SessionResponse(user, token);
    }
}
