using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using plantio.Services;

namespace plantio.Controllers {
    [ApiController]
    [Route("[controller]")]
    public class UserController: ControllerBase {
        private readonly UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        [HttpPost("create")]
        public Task<IActionResult> CreateUser(ChangeUserRequest registerUser) =>
            this.SafeDomainCall(async () => Ok(await this.userService.Register(registerUser)));

        [HttpPost("login")]
        public Task<IActionResult> LoginUser(ChangeUserRequest request) =>
            this.SafeDomainCall(async () => Ok(await this.userService.Login(request)));

        [Authorize]
        [HttpGet("hello")]
        public IActionResult Hello() {
            return Ok("Hola puto");
        }
    }
}
