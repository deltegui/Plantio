const {src} = require('../utils');
const RegisterService = require(src('/users/application/register.service'));
const userErrors = require(src('/users/application/user.errors'));
const {
  userRepositoryFake,
  hasherFake,
  jwtFake,
} = require('./fakes');

describe.only('RegisterService', () => {
  it('should return registered and logged user', async () => {
    const user = {
      name: 'rodrigo',
      password: 'rodrox',
    };
    const expectedJwt = 'myjwtforrodrox';
    const registerService = new RegisterService(
        userRepositoryFake({existsWithNameReturn: false}),
        hasherFake(),
        jwtFake({jwt: expectedJwt}),
    );
    const result = await registerService.registerUser(user);
    expect(result).toMatchObject({
      name: user.name,
      jwt: expectedJwt,
    });
  });

  it('should error if you try create a user that already exists', async () => {
    const user = {
      name: 'manolo',
      password: 'man',
    };
    const registerService = new RegisterService(
        userRepositoryFake({existsWithNameReturn: true}),
        hasherFake(),
        jwtFake(),
    );
    const expectedError = await registerService.registerUser(user);
    expect(expectedError).toBe(userErrors.alreadyExists);
  });
});
