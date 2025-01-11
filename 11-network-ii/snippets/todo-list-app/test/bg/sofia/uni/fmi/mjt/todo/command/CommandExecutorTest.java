package bg.sofia.uni.fmi.mjt.todo.command;

import java.util.Collections;

import bg.sofia.uni.fmi.mjt.todo.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CommandExecutorTest {

    private static final String INVALID_ARGS_COUNT_MESSAGE_FORMAT = "Invalid count of arguments: \"%s\" expects %d arguments. Example: \"%s\"";
    private static final String ADD = "add-todo";
    private static final String COMPLETE = "complete-todo";
    private static final String LIST = "list";

    private Storage storage;
    private CommandExecutor cmdExecutor;

    private String testUser = "user";
    private String testTodo = "todo";
    private int testID = 123;

    private Command add = new Command("add-todo", new String[]{testUser, testTodo});
    private Command complete = new Command("complete-todo", new String[]{testUser, String.format("%d", testID)});
    private Command list = new Command("list", new String[]{testUser});

    @BeforeEach
    public void setUp() {
        storage = mock(Storage.class);
        cmdExecutor = new CommandExecutor(storage);
    }

    @Test
    public void testAddToDo() {
        when(storage.add(testUser, testTodo)).thenReturn(testID);
        String expected = String.format("Added new To Do with ID %s for user %s", testID, testUser);
        String actual = cmdExecutor.execute(add);

        assertEquals(expected, actual, "unexpected output for 'add-todo'");
    }

    @Test
    public void testAddToDoReturnsErrorWhenLessArguments() {
        String expected = String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, ADD, 2, ADD + " <username> <todo_item>");
        String actual = cmdExecutor.execute(new Command("add-todo", new String[]{testUser}));

        assertEquals(expected, actual, "unexpected output for 'complete-todo' when the provided arguments are less than two");
    }

    @Test
    public void testAddToDoReturnsErrorWhenMoreArguments() {
        String expected = String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, ADD, 2, ADD + " <username> <todo_item>");
        String actual = cmdExecutor.execute(new Command("add-todo", new String[]{testUser, testTodo, testTodo}));

        assertEquals(expected, actual, "unexpected output for 'complete-todo' when the provided arguments are more than two");
    }

    @Test
    public void testComplete() {
        String expected = String.format("Completed To Do with ID %s for user %s", testID, testUser);
        String actual = cmdExecutor.execute(complete);

        verify(storage, times(1)).remove(testUser, testID);
        assertEquals(expected, actual, "unexpected output for 'complete-todo' when the user and ID are present in the storage");
    }

    @Test
    public void testCompleteReturnsErrorWhenLessArguments() {
        String expected = String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, COMPLETE, 2, COMPLETE + " <username> <todo_item_id>");
        String actual = cmdExecutor.execute(new Command(COMPLETE, new String[]{testUser}));

        assertEquals(expected, actual, "unexpected output for 'complete-todo' when the provided arguments are less than two");
    }

    @Test
    public void testCompleteReturnsErrorWhenMoreArguments() {
        String expected = String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, COMPLETE, 2, COMPLETE + " <username> <todo_item_id>");
        String actual = cmdExecutor.execute(new Command(COMPLETE, new String[]{testUser, String.format("%d", testID), testTodo}));

        assertEquals(expected, actual, "unexpected output for 'complete-todo' when the provided arguments are more than two");
    }

    @Test
    public void testCompleteReturnsErrorWhenIDIsNotNumerical() {
        String expected = "Invalid ID provided for command \"complete-todo\": only integer values are allowed";
        String actual = cmdExecutor.execute(new Command(COMPLETE, new String[]{testUser, testUser}));

        assertEquals(expected, actual, "unexpected output for 'complete-todo' when the provided to-do ID is not a number");
    }

    @Test
    public void testList() {
        when(storage.list(testUser)).thenReturn(Collections.singletonMap(testID, testTodo));
        String expected = String.format("To-Do list of user %s:%n[%d] %s%n", testUser, testID, testTodo);
        String actual = cmdExecutor.execute(list);

        assertEquals(expected, actual, "unexpected output for 'list' when to-do list for user has one entry");
    }

    @Test
    public void testListWhenEmpty() {
        when(storage.list(testUser)).thenReturn(Collections.emptyMap());
        String expected = "No To-Do items found for user with name " + testUser;
        String actual = cmdExecutor.execute(list);

        assertEquals(expected, actual, "unexpected output for 'list' when to-do list for user is empty");
    }

    @Test
    public void testListReturnsErrorWhenLessArguments() {
        String expected = String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, LIST, 1, LIST + " <username>");
        String actual = cmdExecutor.execute(new Command(LIST, new String[]{}));

        assertEquals(expected, actual, "unexpected output for 'list' with no arguments");
    }

    @Test
    public void testListReturnsErrorWhenMoreArguments() {
        String expected = String.format(INVALID_ARGS_COUNT_MESSAGE_FORMAT, LIST, 1, LIST + " <username>");
        String actual = cmdExecutor.execute(new Command(LIST, new String[]{testUser, testUser}));

        assertEquals(expected, actual, "unexpected output for 'list' with no arguments");
    }

    @Test
    public void testUnknownCommand() {
        String expected = "Unknown command";
        String actual = cmdExecutor.execute(new Command("test", new String[]{}));

        assertEquals(expected, actual, "unexpected output for unknown command");
    }

}
