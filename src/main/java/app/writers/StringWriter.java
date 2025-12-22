package app.writers;

import app.DataWriter;
import app.Settings;

public class StringWriter extends DataWriter {
    private static final String FILE_NAME = "strings.txt";
    private long minLength = Long.MAX_VALUE;
    private long maxLength = Long.MIN_VALUE;

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

    public void printStats() {
        if (settings.isShortStats() || settings.isFullStats()) {
            System.out.println("Файл: " + getFilePath());
            System.out.println("Количество записанных элементов: " + count);
            if (settings.isFullStats() && count > 0) {
                System.out.println("Размер самой короткой строки: " + minLength);
                System.out.println("Размер самой длинной строки: " + maxLength);
            }
        }
    }
}
