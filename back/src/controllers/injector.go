package controllers

import (
	"database/sql"
	"plantio/src/configuration"
	"plantio/src/domain"
	"plantio/src/persistence"
	"plantio/src/tokens"
	"plantio/src/validator"

	"github.com/deltegui/phoenix"
)

func databaseConnection(app phoenix.App) {
	config := app.Injector.Get(configuration.Configuration{}).(configuration.Configuration)
	connection := persistence.ConnectDB(config)
	app.Injector.Add(func() *sql.DB {
		return connection
	})
}

func routes(app phoenix.App) {
	app.MapGroup("/user", func(m phoenix.Mapper) {
		m.Post("/login", LoginUser)
		m.Post("/register", RegisterUser)
		m.Post("/test", TestToken)
	})
	app.Get("404", NotFoundError)
}

func useCases(app phoenix.App) {
	app.Injector.Add(domain.CreateLoginUserCase)
	app.Injector.Add(domain.CreateRegisterUserCase)
	app.Injector.Add(domain.CreateTestValidationCase)
}

func implementations(app phoenix.App) {
	app.Injector.Add(persistence.NewMysqlTokenRepo)
	app.Injector.Add(persistence.NewMysqlUserRepo)
	app.Injector.Add(tokens.NewJWTTokenProvider)
	app.Injector.Add(validator.NewPlaygroundValidator)
}

func Register(app phoenix.App) {
	databaseConnection(app)
	implementations(app)
	useCases(app)
	routes(app)
}
