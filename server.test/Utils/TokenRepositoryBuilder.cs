using System;
using Microsoft.AspNetCore.Identity;
using Moq;
using plantio.Services;
using plantio.Domain;

namespace plantio.Tests.Utils {
    public class TokenRepositoryBuilder {
        private readonly Moq.Mock<TokenRepository> repository = new Moq.Mock<TokenRepository>();

        public TokenRepositoryBuilder WhenSaveDo(Action<Token> action) {
            repository
                .Setup(obj => obj.Save(It.IsAny<Token>()))
                .Callback(action);
            return this;
        }

        public TokenRepositoryBuilder WhenGetTokenReturn(Token token) {
            repository
                .Setup(obj => obj.GetToken(It.IsAny<User>()))
                .Returns<User>(_ => token);
            return this;
        }

        public TokenRepositoryBuilder WhenGetTokenReturn(string tokenValue) {
            this.WhenGetTokenReturn(Token.Create(tokenValue, User.FirstTime("", "")));
            return this;
        }

        public TokenRepositoryBuilder WhenDeleteTokenDo(Action<Token> action) {
            repository
                .Setup(obj => obj.DeleteToken(It.IsAny<Token>()))
                .Callback(action);
            return this;
        }

        public TokenRepository Build() => this.repository.Object;
    }
}