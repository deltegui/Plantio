using System.ComponentModel.DataAnnotations;
using plantio.Domain.Generic;

namespace plantio.Domain.Users {
    public struct SessionRequest {
        [StringLength(255, MinimumLength = 3), Required]
        public string Name { get; set; }
        [StringLength(255, MinimumLength = 3), Required]
        public string Password { get; set; }
    }

    public struct SessionResponse {
        public SessionResponse(string user, string token) {
            User = user;
            Token = token;
        }

        public string Token { get; set; }
        public string User { get; set; }
    }

    public class SessionService {
        private readonly IUserRepository userRepository;
        private readonly IPasswordHasher passwordHasher;
        private readonly ITokenizer tokenizer;

        public SessionService(IUserRepository userRepository, IPasswordHasher passwordHasher, ITokenizer tokenizer) {
            this.userRepository = userRepository;
            this.passwordHasher = passwordHasher;
            this.tokenizer = tokenizer;
        }

        public SessionResponse Register(SessionRequest request) {
            if (this.userRepository.ExistWithName(request.Name)) {
                throw DomainException.FromError(UserErrors.AlreadyExist);
            }
            var user = User.FirstTime(request.Name, request.Password);
            user.Password = this.passwordHasher.HashPassword(user, request.Password);
            this.userRepository.Save(user);
            var token = this.tokenizer.Tokenize(user);
            return new SessionResponse(user.Name, token);
        }

        public SessionResponse Login(SessionRequest request) {
            if (! this.userRepository.ExistWithName(request.Name)) {
                throw DomainException.FromError(UserErrors.NotFound);
            }
            var user = this.userRepository.GetByName(request.Name);
            if (! this.passwordHasher.IsPasswordValid(user, request.Password)) {
                throw DomainException.FromError(UserErrors.InvalidCredentials);
            }
            var token = this.tokenizer.Tokenize(user);
            return new SessionResponse(user.Name, token);
        }
    }
}