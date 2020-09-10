using System.Threading.Tasks;
using plantio.Domain;

namespace plantio.Services {
    public interface TokenRepository {
        Task<bool> Save(Token token);
        Token? GetForUser(User user);
        Task<bool> Delete(Token token);
    }
}