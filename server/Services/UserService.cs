using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;
using plantio.Domain;

namespace plantio.Services{
    public struct ChangeUserRequest {
        public string Name { get; set; }
        public string Password { get; set; }
    }

    public class UserService {
        private readonly UserRepository userRepository;
        private readonly IPasswordHasher<User> passwordHasher;
        private readonly UserTokenizer tokenizer;
        private readonly TokenRepository tokenRepository;

        public UserService(UserRepository userRepository, TokenRepository tokenRepository, IPasswordHasher<User> passwordHasher, UserTokenizer tokenizer) {
            this.userRepository = userRepository;
            this.passwordHasher = passwordHasher;
            this.tokenizer = tokenizer;
            this.tokenRepository = tokenRepository;
        }

        public async Task<User> Register(ChangeUserRequest request) {
            if (await this.userRepository.ExistsWithName(request.Name)) {
                throw DomainException.FromError(UserErrors.AlreadyExist);
            }
            var user = User.FirstTime(request.Name, request.Password);
            user.Password = this.passwordHasher.HashPassword(user, request.Password);
            await this.userRepository.Save(user);
            return user;
        }

        public async Task<string> Login(ChangeUserRequest request) {
            var user = await this.userRepository.GetByName(request.Name);
            if (user == null) {
                throw DomainException.FromError(UserErrors.NotFound);
            }
            var verification = this.passwordHasher.VerifyHashedPassword(user, user.Password, request.Password);
            if (verification == PasswordVerificationResult.Failed) {
                throw DomainException.FromError(UserErrors.InvalidCredentials);
            }
            var token = this.tokenizer.Tokenize(user);
            this.SaveOrReplaceToken(token);
            return token.Value;
        }

        private void SaveOrReplaceToken(Token token) {
            var oldToken = this.tokenRepository.GetToken(token.Owner);
            if (oldToken != null) {
                this.tokenRepository.DeleteToken(oldToken);
            }
            this.tokenRepository.Save(token);
        }
    }
}