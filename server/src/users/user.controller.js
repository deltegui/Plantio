const validator = require('simplejsonvalidator')();

const userValidator = validator.create((t) => ({
  name: t.string.min(1).required,
  password: t.string.required,
}));

class UserController {
  constructor(loginService) {
    this.loginService = loginService;
  }

  login(req, res) {
    const user = req.body;
    if (!userValidator.validate(user)) {
      return res.json({
        code: 0,
        errors: userValidator.errors,
      });
    }
    this.loginService.loginUser(user)
        .then(res.json.bind(res));
  }
}

module.exports = UserController;
