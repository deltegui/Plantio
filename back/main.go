package main

import (
	"plantio/src/configuration"
	"plantio/src/controllers"

	"github.com/deltegui/phoenix"
)

func main() {
	app := phoenix.NewApp()
	app.Configure().
		SetProjectInfo("plantio", "0.1.0").
		EnableLogoFile()
	config := configuration.Load()
	app.Injector.Add(func() configuration.Configuration { return config })
	controllers.Register(app)
	app.Run(config.ListenURL)
}
