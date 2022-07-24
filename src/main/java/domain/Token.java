package domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {
    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty
    private String username="admin";

    @JsonProperty
    private String password="password123";


}
