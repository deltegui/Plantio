package domain

import "time"

type Presenter interface {
	Present(interface{})
	PresentError(error)
}

type Validator interface {
	Validate(interface{}) error
}

type CaseReq interface{}

type AuthReq interface {
	GetToken() string
	SetUser(string)
}

var EmptyReq CaseReq = struct{}{}

type UseCase func(Presenter, CaseReq)

type CaseDecorator func(UseCase) UseCase

type User struct {
	Name           string
	Password       string
	LastConnection time.Time
}

type Token struct {
	User       string
	Value      string
	Expiration time.Time
}

func (token Token) Equals(other Token) bool {
	return token.User == other.User &&
		token.Value == other.Value
}

func (token Token) Expired() bool {
	return token.Expiration.Before(time.Now())
}

type UserRepo interface {
	Save(User) error
	FindByName(string) (User, error)
	Delete(User) error
	ExistsWithName(string) bool
}

type TokenRepo interface {
	Save(Token) error
	FindByUser(string) (Token, error)
	DeleteByUser(string) error
}

type TokenProvider interface {
	GenerateToken(username string) Token
	ExtractInfo(token string) (Token, error)
}
