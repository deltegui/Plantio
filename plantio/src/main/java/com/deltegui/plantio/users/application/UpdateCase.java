package com.deltegui.plantio.users.application;

import com.deltegui.plantio.common.DomainException;
import com.deltegui.plantio.common.UseCase;
import org.springframework.stereotype.Service;

@Service
public class UpdateCase implements UseCase<UpdateRequest, UpdateResponse> {
    private final UserRepository userRepository;

    public UpdateCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UpdateResponse handle(UpdateRequest request) throws DomainException {
        var user = userRepository.findByName(request.getName()).orElseThrow(() -> {
            throw DomainException.fromError(UserErrors.AlreadyExsists);
        });
        user.setBag(request.getBag());
        userRepository.update(user);
        return new UpdateResponse(user);
    }
}
