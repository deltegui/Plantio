const {src} = require('../utils');
const RegisterService = require(src('/users/application/register.service'));
const userErrors = require(src('/users/application/user.errors'));
const {
  userRepositoryFake,
  hasherFake,
  jwtFake,
  tokensRepositoryFake,
} = require('./fakes');

const registerServiceMother = {
  happyPath: (token) => new RegisterService(
      userRepositoryFake({existsWithNameReturn: false}),
      tokensRepositoryFake(),
      hasherFake(),
      jwtFake({jwt: token}),
  ),

  withAlwaysExistsWithName: (token) => new RegisterService(
      userRepositoryFake({existsWithNameReturn: true}),
      tokensRepositoryFake(),
      hasherFake(),
      jwtFake({jwt: token}),
  ),
};

describe('RegisterService', () => {
  it('should return registered and logged user', async () => {
    const user = {
      name: 'rodrigo',
      password: 'rodrox',
    };
    const expectedJwt = 'myjwtforrodrox';
    const registerService = registerServiceMother.happyPath(expectedJwt);
    const result = await registerService.registerUser(user);
    expect(result).toMatchObject({
      name: user.name,
      token: {
        value: expectedJwt,
      },
    });
  });

  it('should error if you try create a user that already exists', () => {
    const user = {
      name: 'manolo',
      password: 'man',
    };
    const registerService = registerServiceMother.withAlwaysExistsWithName();
    const expectedError = registerService.registerUser(user);
    expect(expectedError).rejects.toBe(userErrors.alreadyExists);
  });
});
