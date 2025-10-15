import java.nio.file.*;
import java.io.IOException;

/**
 * Lists files and directories using Java NIO (New I/O) API.
 * 
 * This is a standalone utility class that demonstrates directory listing
 * using the modern java.nio.file package. It lists all entries in the
 * current directory, marking directories with [DIR] prefix and files with
 * spacing for alignment.
 * 
 * Usage: java ListFilesNIO
 * 
 * Note: This class has a main method and can be run independently. It uses
 * DirectoryStream for efficient directory traversal and automatically closes
 * resources using try-with-resources.
 */
public class ListFilesNIO {
    public static void main(String[] args) throws IOException {
        Path dir = Paths.get("."); // Current directory

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                System.out.println(Files.isDirectory(path) ? "[DIR]  " + path.getFileName() : "       " + path.getFileName());
            }
        }
    }
}
