package bg.sofia.uni.fmi.mjt.markdown;

import bg.sofia.uni.fmi.mjt.markdown.converter.BoldConverter;
import bg.sofia.uni.fmi.mjt.markdown.converter.CodeConverter;
import bg.sofia.uni.fmi.mjt.markdown.converter.Converter;
import bg.sofia.uni.fmi.mjt.markdown.converter.HeadersConverter;
import bg.sofia.uni.fmi.mjt.markdown.converter.ItalicConverter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MarkdownConverter implements MarkdownConverterAPI {

    private static final String OPENING_HTML_TAGS = """
        <html>
        <body>""";

    private static final String CLOSING_HTML_TAGS = """
        </body>
        </html>""";

    @Override
    public void convertMarkdown(Reader source, Writer output) {
        BufferedReader reader = new BufferedReader(source);
        PrintWriter writer = new PrintWriter(output);

        writer.println(OPENING_HTML_TAGS);

        String line;

        // 1. Headers
        // 2. Bold
        // 3. Italics
        // 4. Code
        Converter headerConverter = new HeadersConverter();
        List<Converter> formattingConverters = List.of(new BoldConverter(), new ItalicConverter(), new CodeConverter());

        try {
            while ((line = reader.readLine()) != null) {
                if (line.strip().equals("")) {
                    writer.println(line);
                    continue;
                }

                if (headerConverter.isApplicable(line)) {
                    line = headerConverter.apply(line);
                } else {
                    for (Converter converter : formattingConverters) {
                        if (converter.isApplicable(line)) {
                            line = converter.apply(line);
                        }
                    }
                }

                writer.println(line);
            }

            writer.println(CLOSING_HTML_TAGS);
        } catch (IOException ioExc) {
            throw new RuntimeException("Error occurred while processing markdown file", ioExc);
        }
    }

    @Override
    public void convertMarkdown(Path from, Path to) {
        try (var fileReader = new FileReader(from.toString());
             var fileWriter = new FileWriter(to.toString())) {
            convertMarkdown(fileReader, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while converting markdown file " + from.getFileName(), e);
        }
    }

    @Override
    public void convertAllMarkdownFiles(Path sourceDir, Path targetDir) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir, "*.md")) {
            for (Path file : stream) {
                String sourceFileName = file.getFileName().toString();
                String sourceFileNameWithoutExtension = sourceFileName.substring(0, sourceFileName.indexOf("."));
                Path targetFile = targetDir.resolve(sourceFileNameWithoutExtension + ".html");
                convertMarkdown(file, targetFile);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while converting markdown files from dir: " + sourceDir, e);
        }
    }

}
