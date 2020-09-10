using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using plantio.Model;

namespace plantio.Tests.Utils {
    public abstract class IntegrationTest {
        private readonly DbContextOptions<PlantioContext> plantioContextOptions;

        public IntegrationTest() {
            this.plantioContextOptions = new DbContextOptionsBuilder<PlantioContext>()
                .UseInMemoryDatabase("plantio")
                .Options;
        }

        public async Task<PlantioContext> SetupContext() {
            var ctx = new PlantioContext(this.plantioContextOptions);
            await ctx.Database.EnsureDeletedAsync();
            await ctx.Database.EnsureCreatedAsync();
            this.Seed(ctx);
            return new PlantioContext(this.plantioContextOptions);
        }

        protected abstract void Seed(PlantioContext ctx);
    }

}