using System.Threading.Tasks;
using System.Linq;
using System.Collections.Generic;
using plantio.Services;
using plantio.Domain;

namespace plantio.Model {
    public class EFTokenRepository: EFRepository, TokenRepository {
        public EFTokenRepository(PlantioContext ctx): base(ctx) {
        }

        public async void Save(Token token) {
            this.ctx.Tokens.Add(token);
            await this.Sync();
        }

        public async void Delete(Token token) {
            this.ctx.Tokens.Remove(token);
            await this.Sync();
        }

        public Token? GetForUser(User user) {
            IList<Token> tokens = this.ctx.Tokens
                .Where(token => token.Owner.Equals(user))
                .ToList();
            if (tokens.Count <= 0) {
                return null;
            }
            return tokens.First();
        }
    }
}