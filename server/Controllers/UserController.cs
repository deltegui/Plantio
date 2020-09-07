using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using plantio.Services;

namespace plantio.Controllers {
    [ApiController]
    [Route("[controller]")]
    public class UserController : ControllerBase {
        public readonly UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        [HttpPost("create")]
        public async Task<IActionResult> Create(ChangeUserRequest registerUser) {
            try {
                return Ok(await this.userService.Register(registerUser));
            } catch (DomainException domainException) {
                return StatusCode(400, domainException.Error);
            }
        }
    }
}
