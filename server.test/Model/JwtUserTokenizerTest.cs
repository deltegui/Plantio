using System.Collections.Generic;
using Xunit;
using plantio.Model;
using plantio.Domain;

namespace plantio.Tests.Model {
    public class JwtUserTokenizerTest {
        [Theory]
        [MemberData(nameof(Data))]
        public void ShouldReturnNewTokenFromUser(string key, User user) {
            var tokenizer = new JwtUserTokenizer(key);
            var token = tokenizer.Tokenize(user);
            Assert.Equal(ExpectedKeyLength, token.Length);
        }

        public static IEnumerable<object[]> Data => new List<object[]> {
            new object[] { Key, User.FirstTime("Manolo", "olonaM") },
            new object[] { Key, User.FirstTime("Otra c", "maripos") },
        };

        public static string Key => "En boca cerrada no entran moscas pero entran pollas como roscas";
        public static int ExpectedKeyLength => 172;
    }
}