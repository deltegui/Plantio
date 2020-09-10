using System.Threading.Tasks;
using plantio.Domain;

namespace plantio.Services {
    public interface UserRepository {
        Task<bool> ExistsWithName(string name);
        Task<bool> Save(User user);
        Task<bool> Update(User user);
        void Delete(User user);
        Task<User?> GetByName(string name);
    }
}