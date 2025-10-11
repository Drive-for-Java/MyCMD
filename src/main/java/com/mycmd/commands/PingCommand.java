import java.io.BufferedReader;
import java.io.InputStreamReader;

public class PingCommand {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java PingCommand <hostname>");
            return;
        }

        String host = args[0];

        try {
            // Run the ping command
            Process process = Runtime.getRuntime().exec("cmd.exe /c ping " + host);

            // Read the output of the ping command
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Wait for the process to finish
            int exitCode = process.waitFor();
            System.out.println("Ping command exited with code: " + exitCode);

        } catch (Exception e) {
            System.out.println("Error executing ping command.");
            e.printStackTrace();
        }
    }
}
