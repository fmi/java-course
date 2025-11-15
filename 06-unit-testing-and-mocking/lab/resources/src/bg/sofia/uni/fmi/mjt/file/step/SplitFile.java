package bg.sofia.uni.fmi.mjt.file.step;

import java.util.HashSet;
import java.util.Set;
import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.pipeline.step.Step;

/**
 * A pipeline step that splits the content of a {@link File} into multiple
 * smaller {@link File} objects, one for each word (split by whitespace).
 * <p>
 * The resulting {@link File} objects are stored in a {@link Set}. To avoid
 * duplicates
 */
public class SplitFile implements Step<File, Set<File>> {

    private static final String whitespace_regex = "\\s+";

    /**
     * Splits the content of the input {@link File} by whitespace and returns
     * a {@link Set} of new {@link File} objects, each containing one part.
     *
     * @param input the file whose content will be split
     * @return a set of new {@link File} objects containing the split content.
     * No files with duplicate content are included in the set.
     *
     * @throws IllegalArgumentException if the input file or its content is null
     */
    @Override
    public Set<File> process(File input) {
        Set<File> result = new HashSet<>();
        String[] parts = input.getContent().split(whitespace_regex);

        for (String part : parts) {
            result.add(new File(part));
        }

        return result;
    }

}
