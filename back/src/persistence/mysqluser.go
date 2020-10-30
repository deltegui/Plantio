package persistence

import (
	"database/sql"
	"fmt"
	"plantio/src/domain"
	"time"
)

func safetlyExec(conn *sql.DB, query string, params ...interface{}) error {
	_, err := conn.Exec(query, params...)
	if err != nil {
		return fmt.Errorf("Error while inserting user: %s", err)
	}
	return nil
}

type MysqlUserRepo struct {
	conn *sql.DB
}

func NewMysqlUserRepo(conn *sql.DB) domain.UserRepo {
	return MysqlUserRepo{conn}
}

func (repo MysqlUserRepo) Save(user domain.User) error {
	return safetlyExec(
		repo.conn,
		"insert into users (name, password, last_connection) values (?, ?, ?)",
		user.Name,
		user.Password,
		user.LastConnection.Format(time.RFC3339),
	)
}

func (repo MysqlUserRepo) FindByName(name string) (domain.User, error) {
	selectQuery := "select name, password, last_connection from users where name = ?"
	row := repo.conn.QueryRow(selectQuery, name)
	result := domain.User{}
	err := row.Scan(&result.Name, &result.Password, &result.LastConnection)
	if err != nil {
		return domain.User{}, fmt.Errorf("Error while searching user by name: %s", err)
	}
	return result, nil
}

func (repo MysqlUserRepo) ExistsWithName(name string) bool {
	user, err := repo.FindByName(name)
	return err == nil && len(user.Name) != 0
}

func (repo MysqlUserRepo) Delete(user domain.User) error {
	return safetlyExec(repo.conn, "delete from users where name = ?", user.Name)
}
