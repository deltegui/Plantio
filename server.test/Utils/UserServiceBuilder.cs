using System;
using Microsoft.AspNetCore.Identity;
using plantio.Services;
using plantio.Domain;

namespace plantio.Tests.Utils {
    public class UserServiceBuilder {
        private UserRepositoryBuilder userRepository;
        private PasswordHasherBuilder passwordHasher;

        public static ChangeUserRequest DefaultChangeUserRequest { get; } = new ChangeUserRequest() {
            Name = "MyUserExample",
            Password = "MyUserPassword",
        };

        public UserServiceBuilder() {
            this.userRepository = new UserRepositoryBuilder();
            this.passwordHasher = new PasswordHasherBuilder();
        }

        public PasswordHasherBuilderWrapper WithPasswordHasher() {
            return new PasswordHasherBuilderWrapper(this, this.passwordHasher);
        }

        public UserRepositoryBuilderWrapper WithUserRepository() {
            return new UserRepositoryBuilderWrapper(this, this.userRepository);
        }

        public UserService Build() {
            var tokenizer = new FakeUserTokenizer();
            return new UserService(userRepository.Build(), passwordHasher.Build(), tokenizer);
        }
    }

    public class Wrapper {
        private readonly UserServiceBuilder serviceBuilder;

        public Wrapper(UserServiceBuilder serviceBuilder) {
            this.serviceBuilder = serviceBuilder;
        }

        public UserServiceBuilder And() {
            return this.serviceBuilder;
        }
    }

    public class PasswordHasherBuilderWrapper: Wrapper {
        private readonly PasswordHasherBuilder builder;

        public PasswordHasherBuilderWrapper(UserServiceBuilder serviceBuilder, PasswordHasherBuilder builder): base(serviceBuilder) {
            this.builder = builder;
        }

        public PasswordHasherBuilderWrapper WhenHashReturn(string hashed) {
            this.builder.WhenHashReturn(hashed);
            return this;
        }

        public PasswordHasherBuilderWrapper WhenVerifyReturnResult(PasswordVerificationResult result) {
            this.builder.WhenVerifyReturnResult(result);
            return this;
        }
    }

    public class UserRepositoryBuilderWrapper: Wrapper {
        private readonly UserRepositoryBuilder builder;

        public UserRepositoryBuilderWrapper(UserServiceBuilder serviceBuilder, UserRepositoryBuilder builder): base(serviceBuilder) {
            this.builder = builder;
        }

        public UserRepositoryBuilderWrapper WhenExistsWithNameReturn(bool data) {
            this.builder.WhenExistsWithNameReturn(data);
            return this;
        }

        public UserRepositoryBuilderWrapper WhenSaveDo(Action<User> action) {
            this.builder.WhenSaveDo(action);
            return this;
        }

        public UserRepositoryBuilderWrapper WhenGetByNameReturn(User? user) {
            this.builder.WhenGetByNameReturn(user);
            return this;
        }
    }
}