using System;
using Microsoft.AspNetCore.Mvc;
using plantio.Domain.Generic;

namespace plantio.Controllers {

    public static class ControllerBaseErrorMapperExtension {
        public static IActionResult SafeDomainCall (this ControllerBase controller, Func<object> callback) {
            if (! controller.ModelState.IsValid) {
                return controller.BadRequest();
            }
            try {
                return controller.Ok(callback.Invoke());
            } catch (DomainException domainException) {
                return ProblemFromDomainError(controller, domainException.Error);
            }
        }

        private static int MapErrorToStatusCode(DomainError error) =>
            error.Code switch {
                ErrorCode.UserInvalidCredentials => 401,
                ErrorCode.UserNotFound => 404,
                ErrorCode.UserAlreadyExist => 409,
                _ => 400,
            };

        private static ObjectResult ProblemFromDomainError(ControllerBase controller, DomainError error) =>
            controller.Problem(
                detail: error.Fix,
                instance: error.Code.ToString(),
                title: error.Message,
                statusCode: MapErrorToStatusCode(error));
    }
}