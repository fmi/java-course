package bg.sofia.uni.fmi.mjt.markdown;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;

public interface MarkdownConverterAPI {

    /**
     * Converts a text in markdown format readable from {@code source} to a text
     * in corresponding HTML format written to {@code output}.
     *
     * @param source the source character-based input stream
     * @param output the output character-based output stream
     */
    void convertMarkdown(Reader source, Writer output);

    /**
     * Converts a text file in markdown format to a text file in corresponding HTML format.
     *
     * @param from Path of the input file
     * @param to Path of the output file
     */
    void convertMarkdown(Path from, Path to);

    /**
     * Converts all markdown files in a source directory to corresponding HTML files in the target directory.
     * Each markdown file has an extension .md and is converted to an HTML file with the same name
     * and extension .html.
     *
     * @param sourceDir Path of the source directory
     * @param targetDir Path of the target directory
     */
    void convertAllMarkdownFiles(Path sourceDir, Path targetDir);

}
