using System.Threading.Tasks;
using plantio.Domain;

namespace plantio.Services {
    public interface UserRepository {
        Task<bool> ExistsWithName(string name);
        void Save(User user);
        void Update(User user);
        void Delete(User user);
        Task<User?> GetByName(string name);
    }

    public interface UserTokenizer {
        string Tokenize(User user);
    }
}