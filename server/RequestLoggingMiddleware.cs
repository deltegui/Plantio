using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Threading.Tasks;

namespace plantio {
    public class RequestLoggingMiddleware {
        private readonly RequestDelegate next;
        private readonly ILogger logger;

        public RequestLoggingMiddleware(RequestDelegate next, ILoggerFactory loggerFactory) {
            this.next = next;
            this.logger = loggerFactory.CreateLogger<RequestLoggingMiddleware>();
        }

        public async Task Invoke(HttpContext ctx) {
            var req = ctx.Request;
            this.logger.LogInformation($"{req.Protocol} [{req.Method}] {req.Path}");
            await this.next(ctx);
        }
    }

    public static class RequestLoggingMiddlewareExtensions {
        public static IApplicationBuilder UseRequestLogging(this IApplicationBuilder builder) {
            return builder.UseMiddleware<RequestLoggingMiddleware>();
        }
    }
}
