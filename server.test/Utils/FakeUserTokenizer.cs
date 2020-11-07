using System;
using System.Security.Cryptography;
using System.Text;
using plantio.Model;
using plantio.Tokenizer;

namespace plantio.Tests.Utils {
    public class FakeUserTokenizer: UserTokenizer {
        private readonly MD5 md5 = MD5.Create();

        public string Tokenize(User user) {
            md5.ComputeHash(Encoding.ASCII.GetBytes($"{user.Name}::{user.Password}"));
            return Convert.ToBase64String(md5.Hash);
        }
    }
}