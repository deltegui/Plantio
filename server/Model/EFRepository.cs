using System.Threading.Tasks;

namespace plantio.Model {
    public abstract class EFRepository {
        protected readonly PlantioContext ctx;

        public EFRepository(PlantioContext ctx) {
            this.ctx = ctx;
        }

        protected async Task<bool> Sync() {
            int numberOfInsertedEntries = await this.ctx.SaveChangesAsync();
            return numberOfInsertedEntries != 0;
        }
    }
}