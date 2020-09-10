using System.Threading.Tasks;
using System.Linq;
using System.Collections.Generic;
using plantio.Services;
using plantio.Domain;

namespace plantio.Model {
    public class EFUserRepository: EFRepository, UserRepository {
        public EFUserRepository(PlantioContext ctx): base(ctx) {
        }

        public async Task<bool> ExistsWithName(string name) =>
            await this.GetByName(name) != null;

        public async Task<bool> Save(User user) {
            this.ctx.Users.Add(user);
            return await this.Sync();
        }

        public async Task<bool> Update(User user) {
            this.ctx.Users.Update(user);
            return await this.Sync();
        }

        public async Task<bool> Delete(User user) {
            this.ctx.Users.Remove(user);
            return await this.Sync();
        }

        public Task<User?> GetByName(string name) {
            IList<User> users = this.ctx.Users
                .Where(user => user.Name == name)
                .ToList();
            if (users.Count <= 0) {
                return Task.FromResult<User?>(null);
            }
            return Task.FromResult<User?>(users.First());
        }
    }
}