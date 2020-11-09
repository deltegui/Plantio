namespace plantio.Domain.Users {
    public interface IPasswordHasher {
        public string HashPassword(User user, string raw);
        public bool IsPasswordValid(User user, string raw);
    }
}
