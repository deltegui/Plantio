package domain

import "time"

type LoginUserCase UseCase

type loginCase struct {
	userRepo  UserRepo
	tokenRepo TokenRepo
	provider  TokenProvider
}

func CreateLoginUserCase(validator Validator, userRepo UserRepo, tokenRepo TokenRepo, provider TokenProvider) LoginUserCase {
	useCase := loginCase{userRepo, tokenRepo, provider}
	return LoginUserCase(decorate(
		useCase.exec,
		validateDecorator(validator),
	))
}

func (login loginCase) exec(presenter Presenter, req CaseReq) {
	loginReq := req.(*SessionReq)
	user, err := login.userRepo.FindByName(loginReq.Name)
	if err != nil {
		presenter.PresentError(UserNotFound)
		return
	}
	if !checkPassword(loginReq.Password, user.Password) {
		presenter.PresentError(InvalidCredentials)
		return
	}
	token := login.refreshTokenForUser(loginReq.Name)
	presenter.Present(token)
}

func (login loginCase) refreshTokenForUser(user string) Token {
	token, err := login.tokenRepo.FindByUser(user)
	if err != nil {
		token = login.provider.GenerateToken(user)
		login.tokenRepo.Save(token)
	} else if token.Expiration.Before(time.Now()) {
		login.tokenRepo.DeleteByUser(user)
		token = login.provider.GenerateToken(user)
		login.tokenRepo.Save(token)
	}
	return token
}
