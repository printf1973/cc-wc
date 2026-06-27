import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public class ccwc {

    public static String arg, file, content;

    public static void main(String[] args) {

        switch (args.length) {
            case 0:
                showHelp();
                return;

            case 1:
                arg = "-a";
                file = args[0];
                break;

            case 2:
                arg = args[0];
                file = args[1];
                break;

        }

        try {
            Path path = Path.of(file);
            content = Files.readString(path);
        } catch (IOException e) {
            System.out.println("File not found!");
            return;
        }

        switch (arg) {
            case "-c":
                fPrint(file, getBytes());
                break;
            case "-l":
                fPrint(file, getLines());
                break;
            case "-w":
                fPrint(file, getWords());
                break;
            case "-m":
                fPrint(file, content.length());
                break;
            case "-a":
                fPrint(file, getLines(), getWords(), getBytes());
                break;
            default:
                showHelp();
                return;

        }
    }

    public static void showHelp() {
        System.out.println("HELP");
    }

    public static void fPrint(String file, int... n) {
        for (int i : n) {
            System.out.print(i + " ");
        }
        System.out.println(file);
    }

    public static int getBytes() {
        return content.getBytes().length;
    }

    public static int getLines() {
        int lines = 0;
        for (int i = 0; i < content.length(); i++) {
            if (content.charAt(i) == '\n') {
                lines++;
            }
        }
        return lines;
    }

    public static int getWords() {
        if (content == null || content.trim().isEmpty()) {
            return 0;
        }
        return content.trim().split("\\s+").length;

    }
}
