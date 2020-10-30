package persistence

import (
	"database/sql"
	"log"
	"plantio/src/domain"
)

type MysqlTokenRepo struct {
	conn *sql.DB
}

func NewMysqlTokenRepo(conn *sql.DB) domain.TokenRepo {
	return MysqlTokenRepo{conn}
}

func (repo MysqlTokenRepo) Save(token domain.Token) error {
	return safetlyExec(
		repo.conn,
		"insert into tokens (value, expiration, user) values (?, ?, ?)",
		token.Value,
		token.Expiration,
		token.User,
	)
}

func (repo MysqlTokenRepo) FindByUser(username string) (domain.Token, error) {
	selectQuery := "select value, expiration, user from tokens where user = ?"
	row := repo.conn.QueryRow(selectQuery, username)
	result := domain.Token{}
	err := row.Scan(&result.Value, &result.Expiration, &result.User)
	if err != nil {
		log.Printf("Error while searching user by name: %s", err)
		return domain.Token{}, err
	}
	return result, nil
}

func (repo MysqlTokenRepo) DeleteByUser(username string) error {
	return safetlyExec(
		repo.conn,
		"delete from tokens where user = ?",
		username,
	)
}
