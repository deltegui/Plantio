package persistence

import (
	"database/sql"
	"fmt"
	"log"
	"plantio/src/configuration"

	// Mysql driver
	_ "github.com/go-sql-driver/mysql"
)

func ConnectDB(config configuration.Configuration) *sql.DB {
	connWithParams := fmt.Sprintf("%s?parseTime=true", config.DbConn)
	db, err := sql.Open("mysql", connWithParams)
	if err != nil {
		log.Fatalf("Error while connecting to mysql using connection %s. Err: %s", connWithParams, err)
	}
	return db
}
