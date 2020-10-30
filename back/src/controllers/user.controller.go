package controllers

import (
	"net/http"
	"plantio/src/domain"
)

func RegisterUser(registerCase domain.RegisterUserCase) http.HandlerFunc {
	return handlerForSessionRequest(domain.UseCase(registerCase))
}

func LoginUser(loginCase domain.LoginUserCase) http.HandlerFunc {
	return handlerForSessionRequest(domain.UseCase(loginCase))
}

func TestToken(testCase domain.TestValidationCase) http.HandlerFunc {
	var myR domain.MyReq
	return handlerForUseCase(domain.UseCase(testCase), &myR)
}
