using Microsoft.EntityFrameworkCore;
using plantio.Model;
using System.Threading.Tasks;

namespace server.test.Utils {
    public static class DbContextProvider {
        public static async Task<PlantioContext> CreateContext() {
            var plantioContextOptions = new DbContextOptionsBuilder<PlantioContext>()
               .UseInMemoryDatabase("plantio")
               .Options;
            var ctx = new PlantioContext(plantioContextOptions);
            await ctx.Database.EnsureDeletedAsync();
            await ctx.Database.EnsureCreatedAsync();
            return ctx;
        }
    }
}
