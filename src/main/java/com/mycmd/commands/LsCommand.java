import java.nio.file.*;
import java.io.IOException;

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
