using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using plantio.Model;
using server.test.Utils;

namespace plantio.Tests.Utils {
    public abstract class IntegrationTest {

        public async Task<PlantioContext> SetupContext() {
            var ctx = await DbContextProvider.CreateContext();
            this.Seed(ctx);
            return ctx;
        }

        protected abstract void Seed(PlantioContext ctx);
    }

}