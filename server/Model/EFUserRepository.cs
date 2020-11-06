using System.Threading.Tasks;
using System.Linq;
using System.Collections.Generic;
using plantio.Services;
using plantio.Domain;
using Microsoft.EntityFrameworkCore;

namespace plantio.Model {
    public class EFUserRepository: UserRepository {
        private readonly PlantioContext ctx;

        public EFUserRepository(PlantioContext ctx) {
            this.ctx = ctx;
        }

        public async Task<bool> ExistsWithName(string name) =>
            await this.GetByName(name) != null;

        public void Save(User user) {
            this.ctx.Users.Add(user);
            this.ctx.SaveChanges();
        }

        public void Update(User user) {
            this.ctx.Users.Update(user);
            this.ctx.SaveChanges();
        }

        public void Delete(User user) {
            this.ctx.Users.Remove(user);
            this.ctx.SaveChanges();
        }

        public Task<User?> GetByName(string name) {
            IList<User> users = this.ctx.Users
                .Where(user => user.Name == name)
                .ToList();
            if (users.Any()) {
                return Task.FromResult<User?>(users.First());
            }
            return Task.FromResult<User?>(null);
        }
    }
}