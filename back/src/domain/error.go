package domain

import "fmt"

// UseCaseError is an error that can return a UseCase
type UseCaseError struct {
	Code   uint16
	Reason string
	Fix    string
}

func (caseErr UseCaseError) Error() string {
	return fmt.Sprintf("UseCaseError -> [%d] %s: %s", caseErr.Code, caseErr.Reason, caseErr.Fix)
}

var (
	MalformedRequestErr = UseCaseError{Code: 000, Reason: "Bad request", Fix: "See documentation and try again"}
	InternalError       = UseCaseError{Code: 001, Reason: "Internal Error", Fix: ""}
	InvalidCredentials  = UseCaseError{Code: 002, Reason: "Invalid credentials", Fix: "Change credentials and try again"}
)

var (
	UserAlreadyExists = UseCaseError{Code: 100, Reason: "User already exists", Fix: "Use the existing user"}
	UserNotFound      = UseCaseError{Code: 101, Reason: "User not found", Fix: "Create a user and try again"}
	ExpiredToken      = UseCaseError{Code: 102, Reason: "Expired token", Fix: "Login again"}
)
