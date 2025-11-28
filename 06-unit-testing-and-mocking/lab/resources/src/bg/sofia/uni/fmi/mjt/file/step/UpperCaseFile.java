package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.pipeline.step.Step;

/**
 * A pipeline step that transforms the content of a {@link File} to uppercase.
 * <p>
 * This step modifies the {@link File} in place by converting its content
 * to uppercase.
 */
public class UpperCaseFile implements Step<File, File> {

    /**
     * Converts the content of the given {@link File} to uppercase.
     *
     * @param input the file whose content will be transformed
     * @return a new {@link File} object with updated content
     *
     * @throws IllegalArgumentException if the input file or its content is null
     */
    @Override
    public File process(File input) {
        input.getContent().toUpperCase();
        return input;
    }

}
