package bg.sofia.uni.fmi.mjt.file.step;

import java.util.Collection;
import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.pipeline.step.Step;

/**
 * A pipeline step that prints the content of a collection of {@link File} objects
 * to the standard output.
 * <p>
 * This step does not modify the input collection or the files within it. It simply
 * iterates over each {@link File} and prints its content.
 */
public class PrintFiles implements Step<Collection<File>, Collection<File>> {

    /**
     * Prints the content of each {@link File} in the input collection.
     *
     * @param input the collection of files to print
     * @return the same collection of files, unchanged
     *
     * @throws IllegalArgumentException if the input collection is null
     */
    @Override
    public Collection<File> process(Collection<File> input) {
        for (File f : input) {
            System.out.println(f.getContent());
        }

        return input;
    }

}
