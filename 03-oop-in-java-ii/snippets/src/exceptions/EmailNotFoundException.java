package exceptions;

public class EmailNotFoundException extends AuthenticationException {

    public EmailNotFoundException() {
        super("Email not found");
    }

}

