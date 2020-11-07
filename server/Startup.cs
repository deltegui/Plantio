using System.Text;
using Microsoft.IdentityModel.Tokens;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Identity;

using plantio.Model;
using plantio.Tokenizer;
using plantio.Services;
using System;

namespace plantio {
    public class Startup {
        public Startup(IConfiguration configuration) {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        public void ConfigureServices(IServiceCollection services) {
            services.AddMvc();
            services.AddDbContext<PlantioContext>(opt => opt.UseMySQL(Configuration["db:connection"]), ServiceLifetime.Scoped);
            services.AddControllers();
            ConfigureAuthentication(services);
            ConfigureUserServices(services);
        }

        private void ConfigureAuthentication(IServiceCollection services) {
            services.AddAuthentication(options => {
                options.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
                options.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
            }).AddJwtBearer(configure => {
                configure.RequireHttpsMetadata = false;
                configure.SaveToken = true;
                configure.TokenValidationParameters = new TokenValidationParameters() {
                    ValidateIssuerSigningKey = true,
                    IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(Configuration["jwt_secret"])),
                    ValidateIssuer = false,
                    ValidateAudience = false,
                    ValidateLifetime = true,
                    ClockSkew = TimeSpan.Zero,
                };
            });
            services.AddSingleton<UserTokenizer>(services => new JwtUserTokenizer(Configuration["jwt_secret"]));
        }

        private void ConfigureUserServices(IServiceCollection services) {
            services.AddTransient<IPasswordHasher<User>, PasswordHasher<User>>();
            services.AddTransient<UserService>();
        }

        public void Configure(IApplicationBuilder app, IWebHostEnvironment env) {
            if (env.IsDevelopment()) {
                app.UseDeveloperExceptionPage();
            }
            if (env.IsProduction()) {
                app.UseHttpsRedirection();
            }
            app.UseRouting();
            app.UseAuthentication();
            app.UseAuthorization();
            app.UseRequestLogging();
            app.UseEndpoints(endpoints => {
                endpoints.MapControllers();
            });
        }
    }
}
