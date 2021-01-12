package bg.sofia.uni.fmi.mjt.todo.storage;

import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InMemoryStorageTest {

    private Storage storage;

    private final String testUser = "testUser";
    private final String testTodo = "testTodo";
    private int testTodoID;


    @Before
    public void setUp() {
        storage = new InMemoryStorage();
        testTodoID = storage.add(testUser, testTodo);
    }

    @Test
    public void testStorageListWhenUserIsUnknown() {
        Map<Integer, String> expected = Collections.emptyMap();
        Map<Integer, String> actual = storage.list("unknown");

        assertEquals(expected, actual);
    }

    @Test
    public void testStorageList() {
        Map<Integer, String> expected = Collections.singletonMap(testTodoID, testTodo);
        Map<Integer, String> actual = storage.list(testUser);

        assertEquals(expected, actual);
    }

    @Test
    public void testStorageRemove() {
        Map<Integer, String> expectedBefore = Collections.singletonMap(testTodoID, testTodo);
        Map<Integer, String> actualBefore = storage.list(testUser);

        assertEquals("test prerequisite failed: user todo list is not correct", expectedBefore, actualBefore);

        storage.remove(testUser, testTodoID);

        Map<Integer, String> expectedAfter = Collections.emptyMap();
        Map<Integer, String> actualAfter = storage.list(testUser);

        assertEquals(expectedAfter, actualAfter);
    }

    @Test
    public void testStorageRemoveDoesNotChangeWhenUnknownUserIsGiven() {
        Map<Integer, String> expected = Collections.singletonMap(testTodoID, testTodo);
        storage.remove("unknown", 1);
        Map<Integer, String> actual = storage.list(testUser);

        assertEquals(expected, actual);
    }


    @Test
    public void testStorageRemoveDoesNotChangeWhenToDoIDDoesNotExist() {
        Map<Integer, String> expected = Collections.singletonMap(testTodoID, testTodo);
        storage.remove(testUser, 123);
        Map<Integer, String> actual = storage.list(testUser);

        assertEquals(expected, actual);
    }
}
