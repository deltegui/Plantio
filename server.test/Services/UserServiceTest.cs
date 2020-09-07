using System.Collections.Generic;
using Microsoft.AspNetCore.Identity;
using Xunit;
using plantio.Tests.Utils;
using plantio.Services;
using plantio.Domain;

namespace plantio.Tests.Services {
    public class UserServiceTest {

        [Fact]
        public async void RegisterShouldSaveAUser() {
            var request = UserServiceBuilder.DefaultChangeUserRequest;
            User createdUser = new User();
            UserService userService = new UserServiceBuilder()
                .WithUserRepository()
                    .WhenExistsWithNameReturn(false)
                    .WhenSaveDo(user => createdUser = user)
                    .And()
                .WithPasswordHasher()
                    .WhenHashReturn(Hash(request.Password))
                    .And()
                .Build();
            await userService.Register(request);
            Assert.Equal(createdUser.Name, request.Name);
            Assert.Equal(createdUser.Password, Hash(request.Password));
        }

        private string Hash(string raw) => raw + "1";

        [Fact]
        public async void RegisterShouldThrowIfUserAlreadyExists() {
            var userService = new UserServiceBuilder()
                .WithUserRepository()
                    .WhenExistsWithNameReturn(true)
                    .And()
                .Build();
            var exception = await Assert.ThrowsAsync<DomainException>(async () =>
                await userService.Register(new ChangeUserRequest()));
            Assert.Equal(exception.Error, UserErrors.AlreadyExist);
        }

        [Fact]
        public async void LoginShouldReturnATokenForValidUser() {
            string expectedToken = "JC9RVxfQjUbgHmLDseTpaw==";
            var request = UserServiceBuilder.DefaultChangeUserRequest;
            UserService userService = GetUserServiceForLoginOk(request).Build();
            var token = await userService.Login(request);
            Assert.Equal(expectedToken, token);
        }

        [Theory]
        [MemberData(nameof(LoginShouldThrowIfUserCredentialsAreInvalidData))]
        public async void LoginShouldThrowIfUserCredentialsAreInvalid(ChangeUserRequest request, User? returnedUser, DomainError expectedError) {
            var userService = new UserServiceBuilder()
                .WithUserRepository()
                    .WhenGetByNameReturn(returnedUser)
                    .And()
                .Build();
            var exception = await Assert.ThrowsAsync<DomainException>(async () =>
                await userService.Login(request));
            Assert.Equal(exception.Error, expectedError);
        }

        public static IEnumerable<object?[]> LoginShouldThrowIfUserCredentialsAreInvalidData => new List<object?[]> {
            new object?[] { new ChangeUserRequest() { Name = "Manolo", Password = "1234" }, null, UserErrors.NotFound },
            new object?[] { new ChangeUserRequest() { Name = "Javier", Password = "invalid" }, User.FirstTime("Javier", "escuela"), UserErrors.InvalidCredentials },
        };

        [Fact]
        public async void LoginShouldSaveOrReplaceToken() {
            var request = UserServiceBuilder.DefaultChangeUserRequest;
            string expectedHash = "JC9RVxfQjUbgHmLDseTpaw==";
            Token savedToken = new Token();
            var userService = GetUserServiceForLoginOk(request)
                .WithPasswordHasher()
                    .WhenHashReturn(expectedHash)
                    .And()
                .WithTokenRepository()
                    .WhenSaveDo(token => savedToken = token)
                    .And()
                .Build();
            await userService.Login(request);
            Assert.Equal(savedToken.Owner.Name, request.Name);
            Assert.Equal(savedToken.Value, expectedHash);
        }

        private UserServiceBuilder GetUserServiceForLoginOk(ChangeUserRequest request) => new UserServiceBuilder()
            .WithUserRepository()
                .WhenGetByNameReturn(User.FirstTime(request.Name, request.Password))
                .And()
            .WithPasswordHasher()
                .WhenVerifyReturnResult(PasswordVerificationResult.Success)
                .And();
    }
}