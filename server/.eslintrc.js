module.exports = {
  'env': {
    'es2021': true,
    'node': true,
  },
  'plugins': ['jest'],
  'extends': [
    'google',
  ],
  'parserOptions': {
    'ecmaVersion': 12,
    'sourceType': 'module',
  },
  'rules': {
    'require-jsdoc': 0,
  },
};
