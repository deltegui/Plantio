using Microsoft.AspNetCore.Identity;
using plantio.Domain.Users;

namespace plantio.Implementations {
    public class AspPasswordHasher: IPasswordHasher {
        private readonly IPasswordHasher<User> passwordHasher;

        public AspPasswordHasher() {
            this.passwordHasher = new PasswordHasher<User>();
        }

        public string HashPassword(User user, string raw) => this.passwordHasher.HashPassword(user, raw);

        public bool IsPasswordValid(User user, string raw) {
            PasswordVerificationResult result = this.passwordHasher.VerifyHashedPassword(user, user.Password, raw);
            return result == PasswordVerificationResult.Success;
        }
    }
}
