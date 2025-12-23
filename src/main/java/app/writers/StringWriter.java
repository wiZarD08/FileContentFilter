package app.writers;

import app.DataWriter;
import app.Settings;

public class StringWriter extends DataWriter {
    private static final String FILE_NAME = "strings.txt";
    private int minLength = Integer.MAX_VALUE;
    private int maxLength = Integer.MIN_VALUE;

    public StringWriter(Settings settings) {
        super(settings, FILE_NAME);
    }

    public void write(String string) {
        if (super.createPrintWriter()) {
            printWriter.println(string);
            count++;
            if (settings.isFullStats()) {
                int length = string.length();
                if (length < minLength) minLength = length;
                if (length > maxLength) maxLength = length;
            }
        }
    }

    public void printFullStats() {
        System.out.println("Размер самой короткой строки: " + minLength);
        System.out.println("Размер самой длинной строки: " + maxLength);
    }
}
