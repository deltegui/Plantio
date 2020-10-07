const {src} = require('../utils');
const LoginService = require(src('/users/application/login.service'));
const userErrors = require(src('/users/application/user.errors'));
const {
  userRepositoryFake,
  hasherFake,
  jwtFake,
  tokensRepositoryFake,
} = require('./fakes');

describe('LoginService', () => {
  it('should return logged user without password and with token', async () => {
    const user = {
      name: 'rodrigo',
      password: 'rodrox',
    };
    const expectedJwt = 'myjwtforrodrox';
    const hashedPassword = 'jaskj23049$$';
    const loginService = new LoginService(
        userRepositoryFake({
          existsWithNameReturn: true,
          getByNameReturn: {
            name: user.name,
            password: hashedPassword,
            lastConnection: new Date(),
          },
        }),
        tokensRepositoryFake(),
        hasherFake(),
        jwtFake({jwt: expectedJwt}),
    );
    const result = await loginService.loginUser(user);
    expect(result).toMatchObject({
      name: user.name,
      jwt: expectedJwt,
    });
  });

  it('should return error if you login a missing user', async () => {
    const user = {
      name: 'manolo',
      password: 'man',
    };
    const loginService = new LoginService(
        userRepositoryFake({existsWithNameReturn: false}),
        tokensRepositoryFake(),
        hasherFake(),
        jwtFake(),
    );
    const expectedError = await loginService.loginUser(user);
    expect(expectedError).toBe(userErrors.notFound);
  });

  it('should return error if you login with invalid password', async () => {
    const user = {
      name: 'manolo',
      password: 'man',
    };
    const loginService = new LoginService(
        userRepositoryFake({
          existsWithNameReturn: true,
          getByNameReturn: {
            name: user.name,
            password: 'notmine',
          },
        }),
        tokensRepositoryFake(),
        hasherFake({
          checkResult: false,
        }),
        jwtFake(),
    );
    const expectedError = await loginService.loginUser(user);
    expect(expectedError).toBe(userErrors.invalidCredentials);
  });
});
