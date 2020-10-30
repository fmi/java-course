package exceptions;

public class WrongPasswordException extends AuthenticationException {

    public WrongPasswordException() {
        super("Wrong password");
    }

}
