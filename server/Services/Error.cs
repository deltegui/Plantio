namespace plantio.Services {
    public sealed class DomainException: System.Exception {
        public DomainError Error { get; }

        private DomainException(DomainError error) {
            this.Error = error;
        }

        public static DomainException FromError(DomainError error) {
            return new DomainException(error);
        }
    }

    public enum ErrorCode {
        UserNotFound = 100,
        UserAlreadyExist = 101,
        UserInvalidCredentials = 102,
    };

    public sealed class DomainError {
        public ErrorCode Code { get; }
        public string Message { get; }
        public string Fix { get; }

        public DomainError(ErrorCode code, string message, string fix) {
            this.Code = code;
            this.Message = message;
            this.Fix = fix;
        }
    }

    public static class UserErrors {
        public static readonly DomainError NotFound =
            new DomainError(ErrorCode.UserNotFound, "User not found", "Create a user and try again");
        public static readonly DomainError AlreadyExist =
            new DomainError(ErrorCode.UserAlreadyExist, "User already exists", "Use the existing user instead");
        public static readonly DomainError InvalidCredentials =
            new DomainError(ErrorCode.UserInvalidCredentials, "User's name or password are invalid", "Retry");
    }
}