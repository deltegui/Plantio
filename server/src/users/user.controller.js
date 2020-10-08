const validator = require('simplejsonvalidator')();

const userValidator = validator.create((t) => ({
  name: t.string.min(1).required,
  password: t.string.required,
}));

function mapErrorToStatus({code}) {
  const maps = {
    100: 400,
    101: 404,
    102: 400,
  };
  return maps[code];
}

function safeCall(req, res, handler) {
  const user = req.body;
  if (! userValidator.validate(user)) {
    return res.json({
      code: 0,
      errors: userValidator.errors,
    });
  }
  handler(user)
      .then(res.json.bind(res))
      .catch((err) => {
        res.status(mapErrorToStatus(err));
        res.json(err);
      });
};

class UserController {
  constructor(loginService, registerService) {
    this.loginService = loginService;
    this.registerService = registerService;
  }

  login(req, res) {
    const loginUser = this.loginService.loginUser
        .bind(this.loginService);
    return safeCall(req, res, loginUser);
  }

  register(req, res) {
    const registerUser = this.registerService.registerUser
        .bind(this.registerService);
    return safeCall(req, res, registerUser);
  }
}

module.exports = UserController;
