package bg.sofia.uni.fmi.mjt.file.step;

import java.util.Collection;
import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.pipeline.step.Step;

/**
 * A pipeline step that counts the number of {@link File} objects in a collection.
 */
public class CountFiles implements Step<Collection<File>, Integer> {

    /**
     * Returns the number of {@link File} objects in the input collection.
     *
     * @param input the collection of files to count;
     * @return the number of files in the collection
     *
     * @throws IllegalArgumentException if the input collection is null
     */
    @Override
    public Integer process(Collection<File> input) {
        return input.size();
    }

}
