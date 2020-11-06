using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;
using plantio.Domain;

namespace plantio.Services{
    public struct ChangeUserRequest {
        ChangeUserRequest(string name, string password) {
            Name = name;
            Password = password;
        }
        public string Name { get; set; }
        public string Password { get; set; }
    }

    public struct LoginResponse {
        public LoginResponse(string user, string token) {
            User = user;
            Token = token;
        }

        public string Token { get; set; }
        public string User { get; set; }
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

        public async Task<LoginResponse> Register(ChangeUserRequest request) {
            if (await this.userRepository.ExistsWithName(request.Name)) {
                throw DomainException.FromError(UserErrors.AlreadyExist);
            }
            var user = User.FirstTime(request.Name, request.Password);
            user.Password = this.passwordHasher.HashPassword(user, request.Password);
            this.userRepository.Save(user);
            var token = this.tokenizer.Tokenize(user);
            return new LoginResponse(user.Name, token);
        }

        public async Task<LoginResponse> Login(ChangeUserRequest request) {
            User? user = await this.userRepository.GetByName(request.Name);
            if (user == null) {
                throw DomainException.FromError(UserErrors.NotFound);
            }
            if (IsPasswordIncorrect(user, request.Password)) {
                throw DomainException.FromError(UserErrors.InvalidCredentials);
            }
            var token = this.tokenizer.Tokenize(user);
            return new LoginResponse(user.Name, token);
        }

        private bool IsPasswordIncorrect(User user, string password) {
            var verification = this.passwordHasher.VerifyHashedPassword(user, user.Password, password);
            return verification == PasswordVerificationResult.Failed;
        }
    }
}