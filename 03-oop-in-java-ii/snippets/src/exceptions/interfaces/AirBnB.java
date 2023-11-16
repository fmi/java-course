package exceptions.interfaces;

import java.io.IOException;

public class AirBnB implements Bookable {

    @Override
    public boolean book(String request) throws IOException {
        // 1. An overriding method can throw any unchecked exceptions, regardless of whether the overridden method
        //      throws exceptions or not.
        // 2. The overriding method should not throw checked exceptions that are new or broader
        //      than the ones declared by the overridden method.
        // 3. The overriding method can throw narrower or fewer exceptions than the overridden method.
        return true;
    }

}
