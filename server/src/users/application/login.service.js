const userErrors = require('./user.errors');

class LoginService {
  constructor(userRepository, hasher, jwt) {
    this.userRepository = userRepository;
    this.hasher = hasher;
    this.jwt = jwt;
  }

  /**
   * Login a new user itentified only
   * with name and password. Returns
   * login information with JWT, name and
   * last connection.
   * @param {{name: string, password: string}} User to save
   * @return {Promise<{name: String, lastConnection: Date, jwt: String}>}
   */
  async loginUser({name, password}) {
    if (await this.userRepository.existsWithName(name)) {
      return userErrors.alreadyExists;
    }
    const user = await this._save({name, password});
    return {
      name,
      lastConnection: user.lastConnection,
      jwt: this.jwt.generateFor(user),
    };
  }

  async _save({name, password}) {
    const hashed = await this.hasher.hash(password);
    const user = {
      name,
      password: hashed,
      lastConnection: new Date(),
    };
    return this.userRepository.create(user);
  }
}

module.exports = LoginService;
