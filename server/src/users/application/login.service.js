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
    if (!await this.userRepository.existsWithName(name)) {
      return userErrors.notFound;
    }
    const user = await this.userRepository.getByName(name);
    if (!await this.hasher.check(password, user.password)) {
      return userErrors.invalidCredentials;
    }
    await this._updateLastConnection(user);
    return {
      name,
      lastConnection: user.lastConnection,
      jwt: this.jwt.generateFor(user),
    };
  }

  async _updateLastConnection(user) {
    user.lastConnection = new Date();
    await this.userRepository.save(user);
  }
}

module.exports = LoginService;
