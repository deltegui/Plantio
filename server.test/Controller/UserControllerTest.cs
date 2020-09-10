using System.Threading.Tasks;
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
            var req = new ChangeUserRequest() {
                Name = "ExistingUser",
                Password = "Wrong",
            };

            IActionResult result = await controller.CreateUser(req);
            ObjectResult? errResult = result as ObjectResult;

            Assert.NotNull(errResult);
            if (errResult == null) return; // Shutout linter
            Assert.IsType<DomainError>(errResult.Value);
            DomainError error = (DomainError)errResult.Value;
            Assert.Equal(UserErrors.AlreadyExist, error);
            Assert.Equal(StatusCodes.Status409Conflict, errResult.StatusCode);
        }
    }
}