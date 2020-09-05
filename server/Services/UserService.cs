using System.Threading.Tasks;
using Microsoft.AspNetCore.Identity;
using plantio.Domain;

namespace plantio.Services{
    public struct RegisterUserRequest {
        public string Name { get; set; }
        public string Password { get; set; }
    }

    public class UserService {
        private readonly UserRepository userRepository;
        private readonly IPasswordHasher<User> passwordHasher;

        public UserService(UserRepository userRepository, IPasswordHasher<User> passwordHasher) {
            this.userRepository = userRepository;
            this.passwordHasher = passwordHasher;
        }

        public async Task<User> Register(RegisterUserRequest request) {
            if (await this.userRepository.ExistsWithName(request.Name)) {
                throw DomainException.FromError(UserErrors.AlreadyExist);
            }
            var user = User.FirstTime(request.Name, request.Password);
            user.Password = this.passwordHasher.HashPassword(user, request.Password);
            await this.userRepository.Save(user);
            return user;
        }
    }
}