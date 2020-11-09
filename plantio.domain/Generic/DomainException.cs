namespace plantio.Domain.Generic {
    public sealed class DomainException : System.Exception {
        public DomainError Error { get; }

        private DomainException(DomainError error) {
            this.Error = error;
        }

        public static DomainException FromError(DomainError error) {
            return new DomainException(error);
        }
    }
}
