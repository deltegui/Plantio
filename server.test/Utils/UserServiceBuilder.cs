using System;
using Microsoft.AspNetCore.Identity;
using plantio.Services;
using plantio.Domain;
using plantio.Model;

namespace plantio.Tests.Utils {
    public class UserServiceBuilder {
        private UserRepositoryBuilder userRepository;
        private PasswordHasherBuilder passwordHasher;
        private TokenRepositoryBuilder tokenRepository;

        public static ChangeUserRequest DefaultChangeUserRequest { get; } = new ChangeUserRequest() {
            Name = "MyUserExample",
            Password = "MyUserPassword",
        };

        public static UserService BuildProduction(PlantioContext ctx) {
            var userRepo = UserRepositoryBuilder.BuildProduction(ctx);
            var tokenRepo = new TokenRepositoryBuilder().Build(); // TODO change this
            var passwordHasher = new PasswordHasher<User>();
            var tokenizer = new JwtUserTokenizer("bla bla my key bla bla");
            return new UserService(userRepo, tokenRepo, passwordHasher, tokenizer);
        }

        public UserServiceBuilder() {
            this.userRepository = new UserRepositoryBuilder();
            this.passwordHasher = new PasswordHasherBuilder();
            this.tokenRepository = new TokenRepositoryBuilder();
        }

        public PasswordHasherBuilderWrapper WithPasswordHasher() =>
            new PasswordHasherBuilderWrapper(this, this.passwordHasher);

        public UserRepositoryBuilderWrapper WithUserRepository() =>
            new UserRepositoryBuilderWrapper(this, this.userRepository);

        public TokenRepositoryBuilderWrapper WithTokenRepository() =>
            new TokenRepositoryBuilderWrapper(this, this.tokenRepository);

        public UserService Build() => new UserService(
            userRepository.Build(),
            tokenRepository.Build(),
            passwordHasher.Build(),
            new FakeUserTokenizer());
    }

    public class Wrapper {
        private readonly UserServiceBuilder serviceBuilder;

        public Wrapper(UserServiceBuilder serviceBuilder) {
            this.serviceBuilder = serviceBuilder;
        }

        public UserServiceBuilder And() => this.serviceBuilder;
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

    public class TokenRepositoryBuilderWrapper: Wrapper {
        private readonly TokenRepositoryBuilder builder;

        public TokenRepositoryBuilderWrapper(UserServiceBuilder serviceBuilder, TokenRepositoryBuilder builder): base(serviceBuilder) {
            this.builder = builder;
        }

        public TokenRepositoryBuilderWrapper WhenSaveDo(Action<Token> action) {
            this.builder.WhenSaveDo(action);
            return this;
        }

        public TokenRepositoryBuilderWrapper WhenGetTokenReturn(Token token) {
            this.builder.WhenGetTokenReturn(token);
            return this;
        }

        public TokenRepositoryBuilderWrapper WhenGetTokenReturn(string tokenValue) {
            this.builder.WhenGetTokenReturn(tokenValue);
            return this;
        }

        public TokenRepositoryBuilderWrapper WhenDeleteTokenDo(Action<Token> action) {
            this.WhenDeleteTokenDo(action);
            return this;
        }
    }
}