package app.writers;

import app.DataWriter;
import app.Settings;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class IntegerWriter extends DataWriter {
    private static final String FILE_NAME = "integers.txt";
    private long min = Long.MAX_VALUE;
    private long max = Long.MIN_VALUE;
    private BigInteger sum = BigInteger.valueOf(0);

    public IntegerWriter(Settings settings) {
        super(settings, FILE_NAME);
    }

    public void write(long number, String line) {
        if (super.createPrintWriter()) {
            printWriter.println(line);
            count++;
            if (settings.isFullStats()) {
                if (number < min) min = number;
                if (number > max) max = number;
                sum = sum.add(BigInteger.valueOf(number));
            }
        }
    }

    public void printFullStats() {
        System.out.println("Минимальное значение: " + min);
        System.out.println("Максимальное значение: " + max);
        System.out.println("Сумма: " + sum.toString());
        BigDecimal average = new BigDecimal(sum).divide(BigDecimal.valueOf(count), 4, RoundingMode.HALF_UP);
        System.out.println("Среднее значение: " + average);
    }
}
