using Microsoft.IdentityModel.Tokens;
using System;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;
using plantio.Services;
using plantio.Domain;

namespace plantio.Model {
    public class JwtUserTokenizer: UserTokenizer {
        private readonly string secret;

        public JwtUserTokenizer(string key) {
            this.secret = key;
        }

        public Token Tokenize(User user) {
            var rawToken = this.CreateRawToken(user);
            return Token.Create(rawToken, user);
        }

        private string CreateRawToken(User user) {
            var tokenHandler = new JwtSecurityTokenHandler();
            var key = Encoding.UTF8.GetBytes(this.secret);
            var tokenDescriptor = new SecurityTokenDescriptor {
                Subject = new ClaimsIdentity(new[] { new Claim("name", user.Name) }),
                Expires = DateTime.UtcNow.AddDays(7),
                SigningCredentials = new SigningCredentials(new SymmetricSecurityKey(key), SecurityAlgorithms.HmacSha256Signature),
            };
            var token = tokenHandler.CreateToken(tokenDescriptor);
            return tokenHandler.WriteToken(token);
        }
    }
}