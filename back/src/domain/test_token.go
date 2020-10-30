package domain

type MyReq struct {
	Token string
	user  string
}

func (m MyReq) GetToken() string {
	return m.Token
}

func (m *MyReq) SetUser(name string) {
	m.user = name
}

type TestValidationCase UseCase

func CreateTestValidationCase(provider TokenProvider, tokenRepo TokenRepo) TestValidationCase {
	return TestValidationCase(decorate(
		testValidationCase,
		authTokenDecorator(provider, tokenRepo),
	))
}

func testValidationCase(presenter Presenter, req CaseReq) {
	r := req.(*MyReq)
	presenter.Present(r.user)
}
