const validator = require('simplejsonvalidator')();

const userValidator = validator.create((t) => ({
  name: t.string.min(1).required,
  password: t.string.min(6).required,
}));

class UserController {
  login(req, res) {
    const user = req.body;
    if (!userValidator.validate(user)) {
      return res.json({
        error: 'puto',
      });
    }
    res.json(user);
  }
}

module.exports = UserController;
