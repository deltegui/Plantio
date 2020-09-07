using System;

namespace plantio.Domain {
    public class Token {
        private static readonly int elapsedHours = 4;

        public string Value { get; }
        public User Owner { get; }
        public DateTime Expiration { get; }

        public Token(): this("", new User(), DateTime.Now) {
        }

        private Token(string value, User owner, DateTime time) {
            this.Value = value;
            this.Owner = owner;
            this.Expiration = time;
        }

        public static Token Create(string value, User owner) {
            var expiration = DateTime.Now.AddHours(elapsedHours);
            return new Token(value, owner, expiration);
        }
    }
}