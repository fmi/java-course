package bg.sofia.uni.fmi.mjt.todo.storage;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InMemoryStorageTest {

    private Storage storage;

    private final String testUser = "testUser";
    private final String testTodo = "testTodo";
    private int testTodoID;

    @BeforeEach
    public void setUp() {
        storage = new InMemoryStorage();
        testTodoID = storage.add(testUser, testTodo);
    }

    @Test
    public void testStorageListWhenUserIsUnknown() {
        Map<Integer, String> expected = Collections.emptyMap();
        Map<Integer, String> actual = storage.list("unknown");

        assertEquals(expected, actual, "expected empty map for unknown user");
    }

    @Test
    public void testStorageList() {
        Map<Integer, String> expected = Collections.singletonMap(testTodoID, testTodo);
        Map<Integer, String> actual = storage.list(testUser);

        assertEquals(expected, actual, "unexpected map for known user");
    }

    @Test
    public void testStorageRemove() {
        Map<Integer, String> expectedBefore = Collections.singletonMap(testTodoID, testTodo);
        Map<Integer, String> actualBefore = storage.list(testUser);

        assertEquals(expectedBefore, actualBefore, "test prerequisite failed: user todo list is not correct");

        storage.remove(testUser, testTodoID);

        Map<Integer, String> expectedAfter = Collections.emptyMap();
        Map<Integer, String> actualAfter = storage.list(testUser);

        assertEquals(expectedAfter, actualAfter, "expected empty map for user with recently removed item");
    }

    @Test
    public void testStorageRemoveDoesNotChangeWhenUnknownUserIsGiven() {
        Map<Integer, String> expected = Collections.singletonMap(testTodoID, testTodo);
        storage.remove("unknown", 1);
        Map<Integer, String> actual = storage.list(testUser);

        assertEquals(expected, actual, "map shouldn't change when unknown user is given");
    }


    @Test
    public void testStorageRemoveDoesNotChangeWhenToDoIDDoesNotExist() {
        Map<Integer, String> expected = Collections.singletonMap(testTodoID, testTodo);
        storage.remove(testUser, 123);
        Map<Integer, String> actual = storage.list(testUser);

        assertEquals(expected, actual, "map shouldn't change when unknown to-do ID is given");
    }
}
