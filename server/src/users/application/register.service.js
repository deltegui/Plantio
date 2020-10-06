const userErrors = require('./user.errors');

class RegisterService {
  constructor(userRepository, tokenRepository, hasher, jwt) {
    this.userRepository = userRepository;
    this.tokenRepository = tokenRepository;
    this.hasher = hasher;
    this.jwt = jwt;
  }

  /**
   * Register a new user itentified only
   * with name and password.
   * @param {{name: string, password: string}} User to save
   * @return {Promise<{name: String, lastConnection: Date, jwt: String}>}
   */
  async registerUser({name, password}) {
    if (await this.userRepository.existsWithName(name)) {
      return userErrors.alreadyExists;
    }
    const user = await this._save({name, password});
    const token = this.jwt.generateFor(user);
    await this.tokenRepository.save(user, {token, created: new Date()});
    return {
      name,
      lastConnection: user.lastConnection,
      jwt: token,
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

module.exports = RegisterService;
