package configuration

import "github.com/deltegui/configloader"

//Configuration representation of json config file
type Configuration struct {
	ListenURL string `paramName:"url"`
	DbConn    string `paramName:"dbconn"`
	JWTKey    string `paramName:"key"`
}

//Load configuration from config.json file and overwrite
//default values if console params are provided
func Load() Configuration {
	return *configloader.NewConfigLoaderFor(&Configuration{}).
		AddHook(configloader.CreateFileHook("./config.json")).
		AddHook(configloader.CreateParamsHook()).
		AddHook(configloader.CreateEnvHook()).
		Retrieve().(*Configuration)
}
