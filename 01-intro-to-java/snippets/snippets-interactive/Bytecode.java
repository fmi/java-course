/**
 * ============================================================================
 *  LAB 01 · INTERACTIVE SNIPPET
 *  Compile from the console, then look at the raw bytes of the .class file
 * ============================================================================
 *
 *  STEPS
 *  -----
 *   1. Compile and run from the CONSOLE (not the green ▶ button):
 *
 *          javac BytecodeStudent.java
 *          java  BytecodeStudent
 *
 *   2. Open the produced `BytecodeStudent.class` in a hex viewer
 *      (IntelliJ hex view, or from the console:  xxd BytecodeStudent.class | head)
 *
 *   3. Look at the very first 8 bytes.
 *
 *  PREDICT before you open the hex:
 *    • What do you think the first four bytes of EVERY .class file are?  ______
 *    • Somewhere in the first 8 bytes, the Java version that compiled this file
 *      is encoded. Can you spot it / work out the encoding?  ______
 * ============================================================================
 */
public class Bytecode {

    public static void main(String[] args) {
        System.out.println("☕ Hello from the JVM — now read my first 8 bytes.");
    }

}
