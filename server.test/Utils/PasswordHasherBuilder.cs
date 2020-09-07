using Microsoft.AspNetCore.Identity;
using Moq;
using plantio.Domain;

namespace plantio.Tests.Utils {
    public class PasswordHasherBuilder {
        private readonly Moq.Mock<IPasswordHasher<User>> hasher = new Moq.Mock<IPasswordHasher<User>>();

        public PasswordHasherBuilder WhenHashReturn(string hashed) {
            hasher
                .Setup(obj => obj.HashPassword(It.IsAny<User>(), It.IsAny<string>()))
                .Returns<User, string>((User, str) => hashed);
            return this;
        }

        public PasswordHasherBuilder WhenVerifyReturnResult(PasswordVerificationResult result) {
            hasher
                .Setup(obj => obj.VerifyHashedPassword(It.IsAny<User>(), It.IsAny<string>(), It.IsAny<string>()))
                .Returns<User, string, string>((user, hashed, raw) => result);
            return this;
        }

        public IPasswordHasher<User> Build() {
            return this.hasher.Object;
        }
    }
}