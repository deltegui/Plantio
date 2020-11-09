namespace plantio.Domain.Generic {
    public enum ErrorCode {
        UserInvalidCredentials = 102,
        UserNotFound = 100,
        UserAlreadyExist = 101,
    }

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
}