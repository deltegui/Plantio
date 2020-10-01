module.exports = {
    "extends": "airbnb-base",
    "plugins": [
        "mocha"
    ],
    "rules": {
        "keyword-spacing": 0,
        "no-use-before-define": 0,
    },
    "env": {
        "mocha": true
    },
    "globals": {
        "chai": true
    }
};