package exceptions.interfaces;

import java.io.IOException;

public interface Bookable {

    boolean book(String request) throws IOException;

}
