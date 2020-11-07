using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Identity;
using plantio.Model;
using plantio.Tokenizer;

namespace plantio.Services{
    public struct ChangeUserRequest {
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
        private readonly PlantioContext ctx;
        private readonly IPasswordHasher<User> passwordHasher;
        private readonly UserTokenizer tokenizer;

        public UserService(PlantioContext ctx, IPasswordHasher<User> passwordHasher, UserTokenizer tokenizer) {
            this.ctx = ctx;
            this.passwordHasher = passwordHasher;
            this.tokenizer = tokenizer;
        }

        public LoginResponse Register(ChangeUserRequest request) {
            IEnumerable<User> userList = this.ctx.Users.Where(user => user.Name == request.Name);
            if (userList.Any()) {
                throw DomainException.FromError(UserErrors.AlreadyExist);
            }
            var user = User.FirstTime(request.Name, request.Password);
            user.Password = this.passwordHasher.HashPassword(user, request.Password);
            this.ctx.Users.Add(user);
            this.ctx.SaveChanges();
            var token = this.tokenizer.Tokenize(user);
            return new LoginResponse(user.Name, token);
        }

        public LoginResponse Login(ChangeUserRequest request) {
            IEnumerable<User> userList = this.ctx.Users.Where(user => user.Name == request.Name);
            if (! userList.Any()) {
                throw DomainException.FromError(UserErrors.NotFound);
            }
            var user = userList.First();
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