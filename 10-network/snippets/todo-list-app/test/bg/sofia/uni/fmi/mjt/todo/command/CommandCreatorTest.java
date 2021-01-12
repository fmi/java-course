package bg.sofia.uni.fmi.mjt.todo.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CommandCreatorTest {
    @Test
    public void testCommandCreationWithNoArguments() {
        String command = "test";
        Command cmd = CommandCreator.newCommand(command);

        assertEquals("unexpected command returned for command 'test'", command, cmd.command());
        assertNotNull("command arguments should not be null", cmd.arguments());
        assertEquals("unexpected command arguments count", 0, cmd.arguments().length);
    }

    @Test
    public void testCommandCreationWithOneArgument() {
        String command = "test abcd";
        Command cmd = CommandCreator.newCommand(command);

        assertEquals("unexpected command returned for command 'test abcd'", command.split(" ")[0], cmd.command());
        assertNotNull("command arguments should not be null", cmd.arguments());
        assertEquals("unexpected command arguments count", 1, cmd.arguments().length);
        assertEquals("unexpected argument returned for command 'test abcd'", command.split(" ")[1], cmd.arguments()[0]);
    }

    @Test
    public void testCommandCreationWithOneArgumentInQuotes() {
        String command = "test \"abcd 1234\"";
        Command cmd = CommandCreator.newCommand(command);

        assertEquals("unexpected command returned for command 'test \"abcd 1234\"'", command.split(" ")[0], cmd.command());
        assertNotNull("command arguments should not be null", cmd.arguments());
        assertEquals("unexpected command arguments count", 1, cmd.arguments().length);
        assertEquals("multi-word argument is not respected", "abcd 1234", cmd.arguments()[0]);
    }
}
