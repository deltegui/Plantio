const {src} = require('../utils');
const userErrors = require(src('/users/application/user.errors'));

describe('UserErrors', () => {
  it('should export errors', () => {
    expect(userErrors.alreadyExists).toBeTruthly;
    expect(userErrors.invalidCredentials).toBeTruthly;
    expect(userErrors.notFound).toBeTruthly;
  });
});
