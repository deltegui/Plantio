using System;
using System.ComponentModel.DataAnnotations;

namespace plantio.Domain {
    public class User {
        public string Name { get; } = "";
        public string Password { get; set; } = "";
        public DateTime LastConnection { get; private set; } = DateTime.Now;

        public User() {}

        public User(string name, string password, DateTime lastConnection) {
            this.Name = name;
            this.Password = password;
            this.LastConnection = lastConnection;
        }

        public static User FirstTime(string name, string password) {
            return new User(name, password, DateTime.Now);
        }

        public void SetLastConnectionNow() {
            this.LastConnection = DateTime.Now;
        }
    }
}