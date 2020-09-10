using System.Threading.Tasks;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Http;
using Xunit;
using plantio.Tests.Utils;
using plantio.Services;
using plantio.Controllers;
using plantio.Domain;
using plantio.Model;

namespace plantio.Tests.Controller {
    public class UserControllerTest : IntegrationTest {
        private readonly ActionContext actionContext = new ControllerContext {
            HttpContext = new DefaultHttpContext()
        };

        private readonly ChangeUserRequest existingUserRequest = new ChangeUserRequest() {
            Name = "ExistingUser",
            Password = "Blabla",
        };

        protected override async void Seed(PlantioContext ctx) {
            var service = UserServiceBuilder.BuildProduction(ctx);
            await service.Register(new ChangeUserRequest() { Name = "ExistingUser", Password = "Blabla" });
        }

        public async Task<UserController> CreateUserController() {
            var ctx = await this.SetupContext();
            var service = UserServiceBuilder.BuildProduction(ctx);
            return new UserController(service);
        }

        [Fact]
        public async void WhenRegisterShouldSaveAUser() {
            var controller = await CreateUserController();
            var req = new ChangeUserRequest() {
                Name = "ExpectedName",
                Password = "ExpectedPassword",
            };
            IActionResult result = await controller.CreateUser(req);
            ObjectResult? okResult = result as ObjectResult;
            Assert.NotNull(okResult);
            if (okResult == null) return; // Shutout linter
            Assert.IsType<User>(okResult.Value);
            Assert.Equal(StatusCodes.Status200OK, okResult.StatusCode);
        }

        [Fact]
        public async void WhenRegisterShouldThrowIfUserAlreadyExist() {
            var controller = await CreateUserController();
            var req = this.existingUserRequest;

            IActionResult result = await controller.CreateUser(req);
            ObjectResult? errResult = result as ObjectResult;

            Assert.NotNull(errResult);
            if (errResult == null) return; // Shutout linter
            Assert.IsType<DomainError>(errResult.Value);
            DomainError error = (DomainError)errResult.Value;
            Assert.Equal(UserErrors.AlreadyExist, error);
            Assert.Equal(StatusCodes.Status409Conflict, errResult.StatusCode);
        }

        [Fact]
        public async void WhenLoginWithExistingUserShouldReturnAuthToken() {
            var controller = await CreateUserController();
            var req = this.existingUserRequest;

            ObjectResult? result = await controller.LoginUser(req) as ObjectResult;

            Assert.NotNull(result);
            if (result == null) return;
            Assert.IsType<string>(result.Value);
            Assert.Equal(StatusCodes.Status200OK, result.StatusCode);
        }

        [Theory]
        [MemberData(nameof(WhenLoginWithFailureShouldThrowErrorData))]
        public async void WhenLoginWithFailureShouldThrowError(ChangeUserRequest request, int code, DomainError error) {
            var controller = await CreateUserController();

            ObjectResult? result = await controller.LoginUser(request) as ObjectResult;

            Assert.NotNull(result);
            if (result == null) return;
            Assert.IsType<DomainError>(result.Value);
            DomainError resultError = (DomainError)result.Value;
            Assert.Equal(error, resultError);
            Assert.Equal(code, result.StatusCode);
        }

        public static IEnumerable<object?[]> WhenLoginWithFailureShouldThrowErrorData => new List<object?[]> {
            new object?[] { new ChangeUserRequest() { Name = "Manolo", Password = "1234" }, StatusCodes.Status404NotFound, UserErrors.NotFound },
            new object?[] { new ChangeUserRequest() { Name = "ExistingUser", Password = "nope!" }, StatusCodes.Status401Unauthorized, UserErrors.InvalidCredentials },
        };
    }
}