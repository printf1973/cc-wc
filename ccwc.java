import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class ccwc {

    public static String arg = "-a";
    public static String file = "";
    public static int BUFFER_SIZE = 64 * 1024;
    public static int l = 0; // line count
    public static int c = 0; // byte count
    public static int m = 0; // character count
    public static int w = 0; // word count

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

        try (BufferedInputStream reader = (file.equals(""))
                ? new BufferedInputStream(System.in, BUFFER_SIZE)
                : new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);) {

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead = 0;
            boolean inWord = false;
            int utf8BytesLeft = 0;

            while ((bytesRead = reader.read(buffer)) != -1) {
                c += bytesRead; // count bytes

                for (int i = 0; i < bytesRead; i++) {
                    int b = buffer[i] & 0xFF;

                    if (b == '\n') { // count lines
                        l++;
                    }

                    if (utf8BytesLeft == 0) { // count characters
                        m++;
                        if ((b & 0x80) != 0) {
                            if ((b & 0xE0) == 0xC0) {
                                utf8BytesLeft = 1;
                            }

                            else if ((b & 0xF0) == 0xE0) {
                                utf8BytesLeft = 2;
                            }

                            else if ((b & 0xF8) == 0xF0) {
                                utf8BytesLeft = 3;
                            }
                        }

                        else {
                            if ((b & 0xC0) == 0x80) {
                                utf8BytesLeft--;
                            }

                            else {
                                utf8BytesLeft = 0;
                                m++;
                            }
                        }
                    }

                    if (b == ' ' || b == '\t' || b == '\n' || b == '\r') { // count words
                        inWord = false;
                    } else if (!inWord) {
                        inWord = true;
                        w++;
                    }

                }
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
        System.out.println("usage: ccwc [options] [file]");
        System.out.println("options:");
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
