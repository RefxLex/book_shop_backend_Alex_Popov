package com.reflex.model.minor;

public class LoginRequestBody {
	
    public String login;
    public String password;

    public LoginRequestBody(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
