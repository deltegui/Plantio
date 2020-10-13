const {src} = require('../utils');
const LoginService = require(src('/users/application/login.service'));
const userErrors = require(src('/users/application/user.errors'));
const {
  userRepositoryFake,
  hasherFake,
  jwtFake,
  tokensRepositoryFake,
} = require('./fakes');

const loginServiceMother = {
  happyPath: ({user, hashedPassword, token}) =>
    new LoginService(
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
        jwtFake({jwt: token}),
    ),

  missingUser: () => new LoginService(
      userRepositoryFake({existsWithNameReturn: false}),
      tokensRepositoryFake(),
      hasherFake(),
      jwtFake(),
  ),

  invalidPassword: (user) => new LoginService(
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
  ),
};

describe('LoginService', () => {
  it('should return logged user without password and with token', async () => {
    const user = {
      name: 'rodrigo',
      password: 'rodrox',
    };
    const expectedJwt = 'myjwtforrodrox';
    const hashedPassword = 'jaskj23049$$';
    const loginService = loginServiceMother.happyPath({
      user,
      hashedPassword,
      token: expectedJwt,
    });
    const result = await loginService.loginUser(user);
    expect(result).toMatchObject({
      name: user.name,
      token: {
        value: expectedJwt,
      },
    });
  });

  it('should return error if you login a missing user', () => {
    const user = {
      name: 'manolo',
      password: 'man',
    };
    const loginService = loginServiceMother.missingUser();
    const expectedError = loginService.loginUser(user);
    expect(expectedError).rejects.toBe(userErrors.notFound);
  });

  it('should return error if you login with invalid password', () => {
    const user = {
      name: 'manolo',
      password: 'man',
    };
    const loginService = loginServiceMother.invalidPassword(user);
    const expectedError = loginService.loginUser(user);
    expect(expectedError).rejects.toBe(userErrors.invalidCredentials);
  });
});
