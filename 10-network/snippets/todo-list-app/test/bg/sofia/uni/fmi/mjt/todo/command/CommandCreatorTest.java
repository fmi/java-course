package bg.sofia.uni.fmi.mjt.todo.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CommandCreatorTest {
    @Test
    public void testCommandCreationWithNoArguments() {
        String command = "test";
        Command cmd = CommandCreator.newCommand(command);

        assertEquals(command, cmd.command());
        assertNotNull(cmd.arguments());
        assertEquals(0, cmd.arguments().length);
    }

    @Test
    public void testCommandCreationWithOneArgument() {
        String command = "test abcd";
        Command cmd = CommandCreator.newCommand(command);

        assertEquals(command.split(" ")[0], cmd.command());
        assertNotNull(cmd.arguments());
        assertEquals(1, cmd.arguments().length);
        assertEquals(command.split(" ")[1], cmd.arguments()[0]);
    }

    @Test
    public void testCommandCreationWithOneArgumentInQuotes() {
        String command = "test \"abcd 1234\"";
        Command cmd = CommandCreator.newCommand(command);

        assertEquals(command.split(" ")[0], cmd.command());
        assertNotNull(cmd.arguments());
        assertEquals(1, cmd.arguments().length);
        assertEquals("abcd 1234", cmd.arguments()[0]);
    }
}
