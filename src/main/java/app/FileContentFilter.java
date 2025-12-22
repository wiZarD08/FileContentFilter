package app;

import app.writers.FloatWriter;
import app.writers.IntegerWriter;
import app.writers.StringWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileContentFilter {
    private final Settings settings;
    private final IntegerWriter intWriter;
    private final FloatWriter floatWriter;
    private final StringWriter stringWriter;

    public FileContentFilter(Settings settings) {
        this.settings = settings;
        this.intWriter = new IntegerWriter(settings);
        this.floatWriter = new FloatWriter(settings);
        this.stringWriter = new StringWriter(settings);
    }

    public void filter() {
        for (String inputFileName : settings.getInputFileNames()) {
            try (Scanner scanner = new Scanner(new File(inputFileName))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    try {
                        intWriter.write(Long.parseLong(line.trim()), line);
                    } catch (NumberFormatException ignore) {
                        try {
                            floatWriter.write(Double.parseDouble(line.trim()), line);
                        } catch (NumberFormatException ignore1) {
                            stringWriter.write(line);
                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
        }
        List<DataWriter> writers = new ArrayList<>(List.of(intWriter, floatWriter, stringWriter));
        if (!settings.isAppendMode()) {
            writers.forEach(DataWriter::clearFileIfExists);
        }
        writers.forEach(DataWriter::printStats);
        writers.forEach(DataWriter::close);
    }
}
