package rss.manager.model.request;

public class SignUpRequest {
    private final String endpoint = "http://toto.herokuapp.com/v1/signup";
    private String pseudo;
    private String password;

    public SignUpRequest(String pseudo, String password) {
        this.pseudo = pseudo;
        this.password = password;
    }

    public void send() {

    }
}
