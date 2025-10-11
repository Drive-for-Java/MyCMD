import java.io.File;

public class LsCommand {

    public static void main(String[] args) {
        // Determine the directory to list
        String directoryPath = "."; // current directory by default
        if (args.length > 0) {
            directoryPath = args[0]; // if user provides a path
        }

        File directory = new File(directoryPath);

        if (!directory.exists()) {
            System.out.println("Directory does not exist: " + directoryPath);
            return;
        }

        if (!directory.isDirectory()) {
            System.out.println(directoryPath + " is not a directory.");
            return;
        }

        // List files and directories
        String[] contents = directory.list();
        if (contents != null && contents.length > 0) {
            for (String item : contents) {
                System.out.println(item);
            }
        } else {
            System.out.println("Directory is empty.");
        }
    }
}
