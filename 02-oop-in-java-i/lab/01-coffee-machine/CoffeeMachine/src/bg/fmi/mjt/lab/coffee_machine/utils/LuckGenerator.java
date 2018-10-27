package bg.fmi.mjt.lab.coffee_machine.utils;

public class LuckGenerator {
    private int counter;

    private static final String[] LUCKS = { "If at first you don't succeed call it version 1.0.",
            "Today you will make magic happen!", "Have you tried turning it off and on again?",
            "Life would be much more easier if you had the source code." };

    public String generateLuck() {
        if (counter == LUCKS.length) {
            counter = 0;
        }

        return LUCKS[counter++];
    }
}
