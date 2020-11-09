using plantio.Domain.Users;
using plantio.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace plantio.Implementations {
    public class EFUserRepository : IUserRepository {
        private readonly PlantioContext ctx;

        public EFUserRepository(PlantioContext ctx) {
            this.ctx = ctx;
        }

        public bool ExistWithName(string name) => GetByName(name) != null;

        public User? GetByName(string name) {
            IEnumerable<User> users = this.ctx.Users.Where(u => u.Name == name);
            if (users.Any()) {
                return users.First();
            }
            return null;
        }

        public void Save(User user) {
            this.ctx.Add(user);
            this.ctx.SaveChanges();
        }
    }
}
