package bg.sofia.uni.fmi.mjt.todo.storage;

import java.util.Map;

public interface Storage {

    int add(String user, String todo);

    void remove(String user, int todoID);

    Map<Integer, String> list(String user);

}
