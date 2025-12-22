package app.writers;

import app.DataWriter;
import app.Settings;

import java.math.BigInteger;

public class IntegerWriter extends DataWriter {
    private static final String FILE_NAME = "integers.txt";
    private long min = Long.MAX_VALUE;
    private long max = Long.MIN_VALUE;
    private BigInteger sum = BigInteger.valueOf(0);

    public IntegerWriter(Settings settings) {
        super(settings, FILE_NAME);
    }

    public void write(long number) {
        if (super.createPrintWriter()) {
            printWriter.println(number);
            count++;
            if (settings.isFullStats()) {
                if (number < min) min = number;
                if (number > max) max = number;
                sum = sum.add(BigInteger.valueOf(number));
            }
        }
    }

    public void printStats() {
        if (settings.isShortStats() || settings.isFullStats()) {
            System.out.println("Файл: " + getFilePath());
            System.out.println("Количество записанных элементов: " + count);
            if (settings.isFullStats() && count > 0) {
                System.out.println("Минимальное значение: " + min);
                System.out.println("Максимальное значение: " + max);
                System.out.println("Сумма: " + sum.toString());
                System.out.println("Среднее значение: " + sum.divide(BigInteger.valueOf(count)));
            }
            System.out.println();
        }
    }
}
