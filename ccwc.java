import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

public class ccwc {

    public static String arg = "-a";
    public static String file = "";
    public static int BUFFER_SIZE = 64 * 1024;
    public static int l = 0;
    public static int c = 0;
    public static int m = 0;
    public static int w = 0;

    public static void main(String[] args) {

        if (args.length > 0) {
            if (args[0].startsWith("-")) {
                arg = args[0];
                if (args.length > 1) {
                    file = args[1];
                }
            } else {
                file = args[0];
            }
        }

        try (BufferedReader reader = (file.equals(""))
                ? new BufferedReader(new InputStreamReader(System.in), BUFFER_SIZE)
                : new BufferedReader(new FileReader(file), BUFFER_SIZE);) {
            String line;
            while ((line = reader.readLine()) != null) {
                l++;
                c += line.getBytes().length + 2; // +2 because the readLine trims the trailing \n
                m += line.length() + 2;
                w += line.trim().split("\\s+").length;
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("File not found!");
            showHelp();
            return;
        }

        switch (arg) {
            case "-c":
                fPrint(file, c);
                break;
            case "-l":
                fPrint(file, l);
                break;
            case "-w":
                fPrint(file, w);
                break;
            case "-m":
                fPrint(file, m);
                break;
            case "-a":
                fPrint(file, l, w, c);
                break;
            default:
                showHelp();
                return;

        }
    }

    public static void showHelp() {
        System.out.println("usage: ccwc [flags] [file]");
        System.out.println("flags:");
        System.out.println("\t-l: Line count");
        System.out.println("\t-c: Byte count");
        System.out.println("\t-w: Word count");
        System.out.println("\t-m: Character count");
    }

    public static void fPrint(String file, int... n) {
        System.out.print("  ");
        for (int i : n) {
            System.out.print(i + "  ");
        }
        System.out.println(file);
    }

}
