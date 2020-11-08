using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using plantio.Services;

namespace plantio.Controllers {
    [ApiController]
    [Route("users")]
    public class UserController: ControllerBase {
        private readonly UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        [HttpPost("register")]
        public IActionResult CreateUser(ChangeUserRequest registerUser) =>
            this.SafeDomainCall(() => this.userService.Register(registerUser));

        [HttpPost("login")]
        public IActionResult LoginUser(ChangeUserRequest request) =>
            this.SafeDomainCall(() => this.userService.Login(request));

        [Authorize]
        [HttpGet("hello")]
        public IActionResult Hello() {
            return Ok($"Hola {this.User.Identity.Name}");
        }
    }
}
