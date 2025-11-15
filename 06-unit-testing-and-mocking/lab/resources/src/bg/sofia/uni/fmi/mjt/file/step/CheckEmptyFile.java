package bg.sofia.uni.fmi.mjt.file.step;

import bg.sofia.uni.fmi.mjt.file.File;
import bg.sofia.uni.fmi.mjt.file.exception.EmptyFileException;
import bg.sofia.uni.fmi.mjt.pipeline.step.Step;

/**
 * A pipeline step that validates whether a {@link File} is empty.
 */
public class CheckEmptyFile implements Step<File, File> {

    /**
     * Validates that the input {@link File} is not empty.
     *
     * @param input the file to check
     * @return the same {@link File} if it is not empty
     * @throws EmptyFileException with message "Input file or its content is empty or null"
     *                              if the file is null or if the file content
     *                              is empty or null.
     */
    @Override
    public File process(File input) {
        if (input.getContent().isEmpty()) {
            throw new IllegalArgumentException("Input file is empty");
        }

        return input;
    }

}
