package domain

import (
	"log"
)

func decorate(useCase UseCase, decorators ...func(UseCase) UseCase) UseCase {
	if len(decorators) <= 0 {
		return useCase
	}
	chain := decorators[0](useCase)
	for i := 1; i < len(decorators); i++ {
		chain = decorators[i](chain)
	}
	return chain
}

func validateDecorator(validator Validator) func(UseCase) UseCase {
	return func(next UseCase) UseCase {
		return func(presenter Presenter, req CaseReq) {
			if err := validator.Validate(req); err != nil {
				presenter.PresentError(MalformedRequestErr)
				return
			}
			next(presenter, req)
		}
	}
}

func authTokenDecorator(provider TokenProvider, tokenRepo TokenRepo) func(UseCase) UseCase {
	return func(next UseCase) UseCase {
		return func(presenter Presenter, req CaseReq) {
			authReq, ok := req.(AuthReq)
			if !ok {
				log.Println("Something that must be a AuthReq is not")
				presenter.PresentError(MalformedRequestErr)
				return
			}
			token, err := provider.ExtractInfo(authReq.GetToken())
			if err != nil {
				presenter.PresentError(MalformedRequestErr)
				return
			}
			storedToken, err := tokenRepo.FindByUser(token.User)
			if err != nil || !storedToken.Equals(token) {
				presenter.PresentError(InvalidCredentials)
				return
			}
			if storedToken.Expired() {
				presenter.PresentError(ExpiredToken)
				return
			}
			authReq.SetUser(token.User)
			next(presenter, req)
		}
	}
}
