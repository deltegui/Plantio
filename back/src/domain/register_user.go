package domain

import (
	"log"
	"time"

	"golang.org/x/crypto/bcrypt"
)

type SessionReq struct {
	Name     string `validate:"required"`
	Password string `validate:"required"`
}

type RegisterUserCase UseCase

func CreateRegisterUserCase(validator Validator, userRepo UserRepo, tokenRepo TokenRepo, provider TokenProvider) RegisterUserCase {
	return RegisterUserCase(decorate(
		registerUserCase(userRepo, tokenRepo, provider),
		validateDecorator(validator),
	))
}

func registerUserCase(userRepo UserRepo, tokenRepo TokenRepo, provider TokenProvider) UseCase {
	return func(presenter Presenter, req CaseReq) {
		registerReq := req.(*SessionReq)
		if userRepo.ExistsWithName(registerReq.Name) {
			presenter.PresentError(UserAlreadyExists)
			return
		}
		userRepo.Save(User{
			Name:           registerReq.Name,
			Password:       hashPassword(registerReq.Password),
			LastConnection: time.Now(),
		})
		token := provider.GenerateToken(registerReq.Name)
		tokenRepo.Save(token)
		presenter.Present(token)
	}
}

func hashPassword(plainPassword string) string {
	hash, err := bcrypt.GenerateFromPassword([]byte(plainPassword), bcrypt.DefaultCost)
	if err != nil {
		log.Fatalln("Error while hashing password: ", err)
	}
	return string(hash)
}

func checkPassword(plainPassword, hashed string) bool {
	err := bcrypt.CompareHashAndPassword([]byte(hashed), []byte(plainPassword))
	return err == nil
}
