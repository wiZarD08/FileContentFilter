package app.writers;

import app.DataWriter;
import app.Settings;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FloatWriter extends DataWriter {
    private static final String FILE_NAME = "floats.txt";
    private double min = Double.MAX_VALUE;
    private double max = Double.MIN_VALUE;
    private BigDecimal sum = BigDecimal.valueOf(0);

    public FloatWriter(Settings settings) {
        super(settings, FILE_NAME);
    }

    public void write(double number, String line) {
        if (super.createPrintWriter()) {
            printWriter.println(line);
            count++;
            if (settings.isFullStats()) {
                if (number < min) min = number;
                if (number > max) max = number;
                sum = sum.add(BigDecimal.valueOf(number));
            }
        }
    }

    public void printFullStats() {
        System.out.println("Минимальное значение: " + min);
        System.out.println("Максимальное значение: " + max);
        System.out.println("Сумма: " + sum.toString());
        System.out.println("Среднее значение: " + sum.divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP));
    }
}
