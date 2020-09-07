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

        public UserService(UserRepository userRepository, IPasswordHasher<User> passwordHasher, UserTokenizer tokenizer) {
            this.userRepository = userRepository;
            this.passwordHasher = passwordHasher;
            this.tokenizer = tokenizer;
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
            return this.tokenizer.Tokenize(user);
        }
    }
}