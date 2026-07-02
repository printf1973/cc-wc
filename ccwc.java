import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class ccwc {

    public static String arg;
    public static String file = "";
    public static String content = "";
    public static String sep = "  ";
    public static int l = 0;
    public static int c = 0;
    public static int m = 0;
    public static int w = 0;

    public static void main(String[] args) {

        if (System.console() == null) {

            arg = (args.length == 1) ? args[0] : "-a";

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in), 64 * 1024);
                String line;
                while ((line = reader.readLine()) != null) {
                    l++;
                    c += line.getBytes().length;
                    m += line.length() + 1;
                    w += line.trim().split("\\s+").length;
                    content += line;
                }
                reader.close();
            } catch (IOException e) {
                showHelp();
                return;
            }

        }

        else {

            switch (args.length) {

                case 1:
                    arg = "-a";
                    file = args[0];
                    break;

                case 2:
                    arg = args[0];
                    file = args[1];
                    break;

                default:
                    showHelp();
                    return;

            }

            // try {
            // BufferedReader reader = new BufferedReader(new FileReader(file));
            // while (reader.ready()) {
            // content += (char) reader.read();
            // }
            // reader.close();
            // } catch (IOException e) {
            // System.out.println("File not found!");
            // return;
            // }

            try {
                BufferedReader reader = new BufferedReader(new FileReader(file), 64 * 1024);
                String line;
                while ((line = reader.readLine()) != null) {
                    l++;
                    c += line.getBytes().length + 1;
                    m += line.length() + 1;
                    w += line.trim().split("\\s+").length;
                }
                reader.close();
            } catch (IOException e) {
                showHelp();
                return;
            }

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
                fPrint(file, m);
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
        System.out.print(sep);
        for (int i : n) {
            System.out.print(i + sep);
        }
        System.out.println(file);
    }

    public static int getBytes() {
        // return content.getBytes().length;
        return c;
    }

    public static int getLines() {
        // int lines = 0;
        // for (int i = 0; i < content.length(); i++) {
        // if (content.charAt(i) == '\n') {
        // lines++;
        // }
        // }
        // return lines;
        return l;
    }

    public static int getWords() {
        // if (content == null || content.trim().isEmpty()) {
        // return 0;
        // }
        // return content.trim().split("\\s+").length;
        return w;

    }
}
