package exceptions.signature;

import java.io.IOException;

public class ExceptionSignatureBase {

    public void fun(String s) throws MyExceptionBase {

    }

}

class MyExceptionBase extends IOException {

}
