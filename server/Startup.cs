using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.EntityFrameworkCore;
using Microsoft.AspNetCore.Identity;

using plantio.Domain;
using plantio.Model;
using plantio.Services;

namespace plantio {
    public class Startup {
        public Startup(IConfiguration configuration) {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        public void ConfigureServices(IServiceCollection services) {
            services.AddMvc();
            services.AddDbContext<PlantioContext>(opt => opt.UseNpgsql(Configuration["db"]));
            services.AddControllers();
            ConfigureUserServices(services);
        }

        private void ConfigureUserServices(IServiceCollection services) {
            services.AddTransient<IPasswordHasher<User>>(services => new PasswordHasher<User>());
            services.AddTransient<UserRepository>(services => {
                var ctx = services.GetService<PlantioContext>();
                return new EFUserRepository(ctx);
            });
            services.AddTransient<UserService>();
            services.AddTransient<UserTokenizer>(services => new JwtUserTokenizer(Configuration["jwt_secret"]));
        }

        public void Configure(IApplicationBuilder app, IWebHostEnvironment env) {
            if (env.IsDevelopment()) {
                app.UseDeveloperExceptionPage();
            }
            // app.UseHttpsRedirection();
            app.UseRouting();
            app.UseAuthorization();
            app.UseEndpoints(endpoints => {
                endpoints.MapControllers();
            });
        }
    }
}
