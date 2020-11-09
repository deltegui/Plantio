using plantio.Domain.Generic;

namespace plantio.Domain.Users {
    public static class UserErrors {
        public static readonly DomainError NotFound =
            new DomainError(ErrorCode.UserNotFound, "User not found", "Create a user and try again");
        public static readonly DomainError AlreadyExist =
            new DomainError(ErrorCode.UserAlreadyExist, "User already exists", "Use the existing user instead");
        public static readonly DomainError InvalidCredentials =
            new DomainError(ErrorCode.UserInvalidCredentials, "User's name or password are invalid", "Retry");
    }
}
