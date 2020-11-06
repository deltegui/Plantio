using System;
using System.Threading.Tasks;
using plantio.Services;
using Microsoft.AspNetCore.Mvc;

namespace plantio.Controllers {
    public static class ControllerBaseErrorMapperExtension {
        public static async Task<IActionResult> SafeDomainCall (this ControllerBase controllerBase, Func<Task<IActionResult>> callback) {
            try {
                return await callback.Invoke();
            } catch (DomainException domainException) {
                return controllerBase.MapErrorToStatusCode(domainException.Error);
            }
        }

        public static ObjectResult MapErrorToStatusCode(this ControllerBase controller, DomainError error) =>
            error.Code switch {
                ErrorCode.UserInvalidCredentials => controller.StatusCode(401, error),
                ErrorCode.UserNotFound => controller.StatusCode(404, error),
                ErrorCode.UserAlreadyExist => controller.StatusCode(409, error),
                _ => controller.StatusCode(400, error),
            };
    }
}