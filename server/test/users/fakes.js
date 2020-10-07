function hasherFake({
  passwordResult,
  checkResult,
} = {
  passwordResult: 'yes',
  checkResult: true,
}) {
  return {
    hash: async () => passwordResult,
    check: async () => checkResult,
  };
}

function jwtFake({jwt} = {jwt: 'thisismyfakejwt'}) {
  return {
    generateFor: () => jwt,
  };
}

function userRepositoryFake({
  createReturn,
  existsWithNameReturn,
  getByNameReturn,
} = {
  craeteReturn: null,
  existsWithNameReturn: false,
  getByNameReturn: null,
}) {
  return {
    async create(user) {
      return createReturn ? createReturn : user;
    },
    existsWithName: () => existsWithNameReturn,
    getByName: () => getByNameReturn,
    update: () => undefined,
  };
}

function tokensRepositoryFake({
  saveReturn,
} = {
  saveReturn: null,
}) {
  return {
    async save(user, token) {
      return saveReturn ? saveReturn : token;
    },
  };
}

module.exports = {
  hasherFake,
  jwtFake,
  userRepositoryFake,
  tokensRepositoryFake,
};
