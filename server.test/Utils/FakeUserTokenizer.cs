using System;
using System.Security.Cryptography;
using System.Text;
using plantio.Services;
using plantio.Domain;

namespace plantio.Tests.Utils {
    public class FakeUserTokenizer: UserTokenizer {
        private readonly MD5 md5 = MD5.Create();

        public Token Tokenize(User user) {
            md5.ComputeHash(Encoding.ASCII.GetBytes($"{user.Name}::{user.Password}"));
            return Token.Create(Convert.ToBase64String(md5.Hash), user);
        }
    }
}