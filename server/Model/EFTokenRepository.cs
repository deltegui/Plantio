using System.Threading.Tasks;
using System.Linq;
using System.Collections.Generic;
using plantio.Services;
using plantio.Domain;

namespace plantio.Model {
    public class EFTokenRepository: EFRepository, TokenRepository {
        public EFTokenRepository(PlantioContext ctx): base(ctx) {
        }

        public async Task<bool> Save(Token token) {
            this.ctx.Tokens.Add(token);
            return await this.Sync();
        }

        public async Task<bool> Delete(Token token) {
            this.ctx.Tokens.Remove(token);
            return await this.Sync();
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