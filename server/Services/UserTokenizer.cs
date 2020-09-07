using plantio.Domain;

namespace plantio.Services {
    public interface UserTokenizer {
        string Tokenize(User user);
    }
}