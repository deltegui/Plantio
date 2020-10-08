const userErrors = require('./user.errors');

class LoginService {
  constructor(userRepository, tokenRepository, hasher, tokenizer) {
    this.userRepository = userRepository;
    this.tokenRepository = tokenRepository;
    this.hasher = hasher;
    this.tokenizer = tokenizer;
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
    if (! await this.userRepository.existsWithName(name)) {
      throw userErrors.notFound;
    }
    const user = await this.userRepository.getByName(name);
    if (! await this.hasher.check(password, user.password)) {
      throw userErrors.invalidCredentials;
    }
    await this._updateLastConnection(user);
    const token = this.tokenizer.generateFor(user);
    this.tokenRepository.save(user, token);
    return {
      name,
      lastConnection: user.lastConnection,
      token,
    };
  }

  async _updateLastConnection(user) {
    user.lastConnection = new Date();
    await this.userRepository.update(user);
  }
}

module.exports = LoginService;
