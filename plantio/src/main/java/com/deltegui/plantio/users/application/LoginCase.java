package com.deltegui.plantio.users.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import org.springframework.stereotype.Service;

@Service
public final class LoginCase implements UseCase<SessionRequest, SessionResponse> {
    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;
    private final TokenProvider tokenProvider;

    public LoginCase(UserRepository userRepository, PasswordHasher hasher, TokenProvider provider) {
        this.userRepository = userRepository;
        this.passwordHasher = hasher;
        this.tokenProvider = provider;
    }

    @Override
    public SessionResponse handle(SessionRequest request) throws DomainException {
        var user = userRepository.findByName(request.getName())
                .orElseThrow(() -> DomainException.fromError(UserErrors.NotFound));
        if (! passwordHasher.matches(user.getPassword(), request.getPassword())) {
            throw DomainException.fromError(UserErrors.InvalidPassword);
        }
        var token = tokenProvider.generateToken(user);
        return new SessionResponse(user.getName(), token);
    }
}
