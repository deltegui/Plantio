using System.Collections.Generic;
using Microsoft.AspNetCore.Identity;
using Xunit;
using plantio.Tests.Utils;
using plantio.Services;
using plantio.Model;

namespace plantio.Tests.Services {
    public class UserServiceTest {

        [Fact]
        public async void RegisterShouldSaveAUser() {
            var request = UserServiceBuilder.DefaultChangeUserRequest;
            User createdUser = new User();
            UserService userService = await new UserServiceBuilder()
                .WithPasswordHasher()
                    .WhenHashReturn(Hash(request.Password))
                    .And()
                .Build();
            userService.Register(request);
            Assert.Equal(createdUser.Name, request.Name);
            Assert.Equal(createdUser.Password, Hash(request.Password));
        }

        private string Hash(string raw) => raw + "1";

        [Fact]
        public async void RegisterShouldThrowIfUserAlreadyExists() {
            var userService = await new UserServiceBuilder().Build();
            var exception = Assert.Throws<DomainException>(() => userService.Register(new ChangeUserRequest()));
            Assert.Equal(exception.Error, UserErrors.AlreadyExist);
        }

        [Fact]
        public async void LoginShouldReturnATokenForValidUser() {
            string expectedToken = "JC9RVxfQjUbgHmLDseTpaw==";
            var request = UserServiceBuilder.DefaultChangeUserRequest;
            UserService userService = await GetUserServiceForLoginOk(request).Build();
            var response = userService.Login(request);
            Assert.Equal(expectedToken, response.Token);
        }

        [Theory]
        [MemberData(nameof(LoginShouldThrowIfUserCredentialsAreInvalidData))]
        public async void LoginShouldThrowIfUserCredentialsAreInvalid(ChangeUserRequest request, DomainError expectedError) {
            var userService = await new UserServiceBuilder().Build();
            var exception = Assert.Throws<DomainException>(() => userService.Login(request));
            Assert.Equal(exception.Error, expectedError);
        }

        public static IEnumerable<object?[]> LoginShouldThrowIfUserCredentialsAreInvalidData => new List<object?[]> {
            new object?[] { new ChangeUserRequest() { Name = "Manolo", Password = "1234" }, UserErrors.NotFound },
            new object?[] { new ChangeUserRequest() { Name = "Javier", Password = "invalid" }, UserErrors.InvalidCredentials },
        };

        private UserServiceBuilder GetUserServiceForLoginOk(ChangeUserRequest request) => new UserServiceBuilder()
            .WithPasswordHasher()
                .WhenVerifyReturnResult(PasswordVerificationResult.Success)
                .And();
    }
}