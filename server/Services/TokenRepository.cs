using plantio.Domain;

namespace plantio.Services {
    public interface TokenRepository {
        void Save(Token token);
        Token? GetForUser(User user);
        void Delete(Token token);
    }
}