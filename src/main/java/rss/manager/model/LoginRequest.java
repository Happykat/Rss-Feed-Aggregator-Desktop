package rss.manager.model;

public class LoginRequest {
    private final String endpoint_ = "http://toto.herokuapp.com/v1/login";
    private String username_;
    private String password_;

    public LoginRequest(String username, String password) {
        username_ = username;
        password_ = password;
    }

    public boolean send() {
        return true;
    }
}
