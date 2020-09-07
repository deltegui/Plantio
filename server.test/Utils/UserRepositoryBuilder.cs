using System;
using System.Threading.Tasks;
using Moq;
using plantio.Services;
using plantio.Domain;


namespace plantio.Tests.Utils {
    public class UserRepositoryBuilder {
        private readonly Moq.Mock<UserRepository> repository = new Moq.Mock<UserRepository>();

        public UserRepositoryBuilder WhenExistsWithNameReturn(bool data) {
            repository
                .Setup(obj => obj.ExistsWithName(It.IsAny<string>()))
                .Returns<string>(_ => Task.FromResult(data));
            return this;
        }

        public UserRepositoryBuilder WhenSaveDo(Action<User> action) {
            repository
                .Setup(obj => obj.Save(It.IsAny<User>()))
                .Callback<User>(action);
            return this;
        }

        public UserRepositoryBuilder WhenGetByNameReturn(User? user) {
            repository
                .Setup(obj => obj.GetByName(It.IsAny<string>()))
                .Returns<string>(_ => Task.FromResult<User?>(user));
            return this;
        }

        public UserRepository Build() => this.repository.Object;
    }
}