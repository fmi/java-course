package exceptions;

public class Main {

    public static void main(String[] args) {
        handleExceptions();
    }

    private static void handleExceptions() {
        // Good - catches the most specific exception
        // We are sure an error occurred because the password was wrong
        try {
            throw new WrongPasswordException();
        } catch (WrongPasswordException e) {
            System.out.println("\n1:");
            System.out.println("Just to be sure -> " + e.getClass().getTypeName());
        }

        // Bad - catches a generic authentication exception
        // We have no idea what kind of authentication error occurred
        try {
            throw new EmailNotFoundException();
        } catch (AuthenticationException e) {
            System.out.println("\n2:");
            System.out.println("AuthenticationException happened to be -> " + e.getClass().getTypeName());
        }

        try {
            throw new WrongPasswordException();
        } catch (AuthenticationException e) {
            System.out.println("\n3:");
            System.out.println("AuthenticationException happened to be -> " + e.getClass().getTypeName());
        }

        try {
            throwsEmailNotFoundException();
            throwsWrongPasswordException();
        } catch (EmailNotFoundException | WrongPasswordException e) {
            System.out.println("\n4:");
            System.out.println("AuthenticationException happened to be -> " + e.getClass().getTypeName());
        }

        // Awful - catches every exception
        // We have no idea what error occurred
        try {
            throw new EmailNotFoundException();
        } catch (Exception e) {
            System.out.println("\n5:");
            System.out.println("Exception happened to be -> " + e.getClass().getTypeName());
        }
        // ==========================================

        // Finally is always executed
        try {
            doesntThrowException();
        } catch (WrongPasswordException e) {
            System.out.println(e.getClass().getTypeName() + " was caught");
        } finally {
            System.out.println("\n6:");
            System.out.println("Finally is executed despite an exception wasn't thrown");
        }

        try {
            throw new WrongPasswordException();
        } catch (WrongPasswordException e) {
            System.out.println("\n7:");
            System.out.println("" + e.getClass().getTypeName() + " was caught");
        } finally {
            System.out.println("Finally is executed after the exception is caught");
        }

        // Finally is executed even though we return in catch
        System.out.println("\n8: " + getNumber());
    }

    private static int getNumber() {
        try {
            throw new Exception();
//            return 1;  -> Unreachable
        } catch (Exception e) {
            return 2;
        } finally {
            return 3;
        }
    }

    private static void throwsEmailNotFoundException() throws EmailNotFoundException {
        throw new EmailNotFoundException();
    }

    private static void throwsWrongPasswordException() throws WrongPasswordException {
        throw new WrongPasswordException();
    }

    private static void doesntThrowException() throws WrongPasswordException { }

}
