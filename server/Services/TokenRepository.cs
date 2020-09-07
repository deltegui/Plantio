using plantio.Domain;

namespace plantio.Services {
    public interface TokenRepository {
        void Save(Token token);
        Token? GetToken(User user);
        void DeleteToken(Token token);
    }
}