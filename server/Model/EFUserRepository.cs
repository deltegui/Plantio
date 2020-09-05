using System.Threading.Tasks;
using System.Linq;
using System.Collections.Generic;
using plantio.Services;
using plantio.Domain;

namespace plantio.Model {
    public class EFUserRepository: UserRepository {
        private readonly PlantioContext ctx;

        public EFUserRepository(PlantioContext ctx) {
            this.ctx = ctx;
        }

        public override async Task<bool> Save(User user) {
            this.ctx.Users.Add(user);
            return await this.Sync();
        }

        public override async Task<bool> Update(User user) {
            this.ctx.Users.Update(user);
            return await this.Sync();
        }

        public override async void Delete(User user) {
            this.ctx.Users.Remove(user);
            await this.Sync();
        }

        public override Task<User?> GetByName(string name) {
            IList<User> users = this.ctx.Users
                .Where(user => user.Name == name)
                .ToList();
            if (users.Count <= 0) {
                return Task.FromResult<User?>(null);
            }
            return Task.FromResult<User?>(users.First());
        }

        private async Task<bool> Sync() {
            int numberOfInsertedEntries = await this.ctx.SaveChangesAsync();
            return numberOfInsertedEntries != 0;
        }
    }
}