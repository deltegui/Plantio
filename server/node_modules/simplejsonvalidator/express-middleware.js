const defaultJsonError = {
  status: 400,
  message: 'invalid json',
};

function sendInvalidJsonError(res, jsonError, statusCode) {
  const status = jsonError.status || statusCode;
  res.status(status).send(jsonError);
}

function expressMiddleware(
    validatorObj,
    jsonError = defaultJsonError,
    statusCode = 400,
) {
  return (req, res, next) => {
    if (validatorObj.validate(req.body)) {
      next();
    } else sendInvalidJsonError(res, jsonError, statusCode);
  };
}

module.exports = expressMiddleware;
