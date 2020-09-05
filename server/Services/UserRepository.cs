using System.Threading.Tasks;
using plantio.Domain;

namespace plantio.Services {
    public abstract class UserRepository {
        public virtual async Task<bool> ExistsWithName(string name) =>
            await this.GetByName(name) != null;

        public abstract Task<bool> Save(User user);
        public abstract Task<bool> Update(User user);
        public abstract void Delete(User user);
        public abstract Task<User?> GetByName(string name);
    }
}