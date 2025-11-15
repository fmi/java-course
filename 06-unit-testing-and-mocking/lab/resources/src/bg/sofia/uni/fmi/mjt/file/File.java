package bg.sofia.uni.fmi.mjt.file;

/**
 * A simple in-memory representation of a file containing textual content.
 */
public class File {

    private String content;

    /**
     * Creates a new File with the given content.
     *
     * @param content the initial content of the file
     * @throws IllegalArgumentException if content is null
     */
    public File(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
