package exceptions.signature;

public class ExceptionSignature extends ExceptionSignatureBase {

    @Override
    public void fun(String s) throws MyException {
        try {
            super.fun(s);
        } catch (MyExceptionBase e) {
            throw new RuntimeException(e);
        }
    }

}

class MyException extends MyExceptionBase {

}
