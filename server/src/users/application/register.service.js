const userErrors = require('./user.errors');

class RegisterService {
  constructor(userRepository, tokenRepository, hasher, tokenizer) {
    this.userRepository = userRepository;
    this.tokenRepository = tokenRepository;
    this.hasher = hasher;
    this.tokenizer = tokenizer;
  }

  /**
   * Register a new user itentified only
   * with name and password.
   * @param {{name: string, password: string}} User to save
   * @return {Promise<{name: String, lastConnection: Date, jwt: String}>}
   */
  async registerUser({name, password}) {
    if (await this.userRepository.existsWithName(name)) {
      throw userErrors.alreadyExists;
    }
    const user = await this._save({name, password});
    const token = this.tokenizer.generateFor(user);
    await this.tokenRepository.save(user, token);
    return {
      name,
      lastConnection: user.lastConnection,
      token,
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
