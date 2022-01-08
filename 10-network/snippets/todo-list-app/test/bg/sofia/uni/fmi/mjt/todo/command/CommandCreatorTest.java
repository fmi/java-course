package bg.sofia.uni.fmi.mjt.todo.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommandCreatorTest {

    @Test
    public void testCommandCreationWithNoArguments() {
        String command = "test";
        Command cmd = CommandCreator.newCommand(command);

        assertEquals(command, cmd.command(), "unexpected command returned for command 'test'");
        assertNotNull(cmd.arguments(), "command arguments should not be null");
        assertEquals(0, cmd.arguments().length, "unexpected command arguments count");
    }

    @Test
    public void testCommandCreationWithOneArgument() {
        String command = "test abcd";
        Command cmd = CommandCreator.newCommand(command);

        assertEquals(command.split(" ")[0], cmd.command(), "unexpected command returned for command 'test abcd'");
        assertNotNull(cmd.arguments(), "command arguments should not be null");
        assertEquals(1, cmd.arguments().length, "unexpected command arguments count");
        assertEquals(command.split(" ")[1], cmd.arguments()[0], "unexpected argument returned for command 'test abcd'");
    }

    @Test
    public void testCommandCreationWithOneArgumentInQuotes() {
        String command = "test \"abcd 1234\"";
        Command cmd = CommandCreator.newCommand(command);

        assertEquals(command.split(" ")[0], cmd.command(), "unexpected command returned for command 'test \"abcd 1234\"'");
        assertNotNull(cmd.arguments(), "command arguments should not be null");
        assertEquals(1, cmd.arguments().length, "unexpected command arguments count");
        assertEquals("abcd 1234", cmd.arguments()[0], "multi-word argument is not respected");
    }

}
