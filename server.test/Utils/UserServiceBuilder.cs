using System;
using Microsoft.AspNetCore.Identity;
using plantio.Services;
using plantio.Tokenizer;
using plantio.Model;
using Microsoft.EntityFrameworkCore;
using server.test.Utils;
using System.Threading.Tasks;

namespace plantio.Tests.Utils {
    public class UserServiceBuilder {
        private PasswordHasherBuilder passwordHasher;

        public static ChangeUserRequest DefaultChangeUserRequest { get; } = new ChangeUserRequest() {
            Name = "MyUserExample",
            Password = "MyUserPassword",
        };

        public static UserService BuildProduction(PlantioContext ctx) {
            var passwordHasher = new PasswordHasher<User>();
            var tokenizer = new JwtUserTokenizer("bla bla my key bla bla");
            return new UserService(ctx, passwordHasher, tokenizer);
        }

        public UserServiceBuilder() {
            this.passwordHasher = new PasswordHasherBuilder();
        }

        public PasswordHasherBuilderWrapper WithPasswordHasher() =>
            new PasswordHasherBuilderWrapper(this, this.passwordHasher);

        public async Task<UserService> Build() => new UserService(
            await DbContextProvider.CreateContext(),
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
}