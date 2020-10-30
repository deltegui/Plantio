package tokens

import (
	"fmt"
	"log"
	"plantio/src/configuration"
	"plantio/src/domain"
	"time"

	"github.com/dgrijalva/jwt-go"
)

type JWTTokenProvider struct {
	config configuration.Configuration
}

func NewJWTTokenProvider(config configuration.Configuration) domain.TokenProvider {
	return JWTTokenProvider{config}
}

func (provider JWTTokenProvider) GenerateToken(username string) domain.Token {
	expiration := time.Now().Add(time.Minute)
	signer := jwt.NewWithClaims(jwt.SigningMethodHS256, jwt.MapClaims{
		"iss": "plantio",
		"sub": username,
		"exp": expiration.Format(time.RFC3339),
	})
	token, err := signer.SignedString([]byte(provider.config.JWTKey))
	if err != nil {
		log.Fatalf("Error while signing token: %s\n", err)
	}
	return domain.Token{
		User:       username,
		Expiration: expiration,
		Value:      token,
	}
}

func (provider JWTTokenProvider) ExtractInfo(token string) (domain.Token, error) {
	result, err := jwt.Parse(token, provider.checkTokenSignAlgorithm)
	if err != nil {
		return domain.Token{}, fmt.Errorf("Error while parsing token %s: %s", token, err)
	}
	if claims, ok := result.Claims.(jwt.MapClaims); ok && result.Valid {
		return provider.parseClaims(claims, token)
	}
	return domain.Token{}, fmt.Errorf("Error while parsing token %s: %s", token, err)
}

func (provider JWTTokenProvider) checkTokenSignAlgorithm(t *jwt.Token) (interface{}, error) {
	if _, ok := t.Method.(*jwt.SigningMethodHMAC); !ok {
		return nil, fmt.Errorf("Invalid signin method %s", t.Header["alg"])
	}
	return []byte(provider.config.JWTKey), nil
}

func (provider JWTTokenProvider) parseClaims(claims jwt.MapClaims, token string) (domain.Token, error) {
	parsedTime, err := time.Parse(time.RFC3339, claims["exp"].(string))
	if err != nil {
		return domain.Token{}, fmt.Errorf("Error while reading expiration claim: %s", err)
	}
	return domain.Token{
		User:       claims["sub"].(string),
		Expiration: parsedTime,
		Value:      token,
	}, nil
}
