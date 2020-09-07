using plantio.Domain;

namespace plantio.Services {
    public interface UserTokenizer {
        Token Tokenize(User user);
    }
}