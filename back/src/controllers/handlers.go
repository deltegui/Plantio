package controllers

import (
	"encoding/json"
	"net/http"
	"plantio/src/domain"

	"github.com/deltegui/phoenix"
)

func handlerForSessionRequest(execCase domain.UseCase) func(w http.ResponseWriter, req *http.Request) {
	var sessionReq domain.SessionReq
	return handlerForUseCase(execCase, &sessionReq)
}

func handlerForUseCase(execCase domain.UseCase, domainRequest interface{}) func(w http.ResponseWriter, req *http.Request) {
	return func(w http.ResponseWriter, req *http.Request) {
		presenter := phoenix.NewJSONPresenter(w)
		if err := json.NewDecoder(req.Body).Decode(domainRequest); err != nil {
			presenter.PresentError(domain.MalformedRequestErr)
			return
		}
		execCase(presenter, domainRequest)
	}
}
