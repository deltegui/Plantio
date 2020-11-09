using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using plantio.Domain.Users;

namespace plantio.Controllers {
    [ApiController]
    [Route("users")]
    public class UserController: ControllerBase {
        private readonly SessionService userService;

        public UserController(SessionService userService) {
            this.userService = userService;
        }

        [HttpPost("register")]
        public IActionResult CreateUser(SessionRequest registerUser) =>
            this.SafeDomainCall(() => this.userService.Register(registerUser));

        [HttpPost("login")]
        public IActionResult LoginUser(SessionRequest request) =>
            this.SafeDomainCall(() => this.userService.Login(request));

        [Authorize]
        [HttpGet("hello")]
        public IActionResult Hello() {
            return Ok($"Hola {this.User.Identity.Name}");
        }
    }
}
