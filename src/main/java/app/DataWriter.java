package app;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class DataWriter {
    protected final Settings settings;
    protected final String filePath;
    protected PrintWriter printWriter;
    protected long count;
    private boolean error;

    protected DataWriter(Settings settings, String fileName) {
        this.settings = settings;
        this.filePath = buildFilePath(fileName, settings);
    }

    private String buildFilePath(String fileName, Settings settings) {
        StringBuilder builder = new StringBuilder();
        String path = settings.getOutputPath();
        if (path != null) {
            builder.append(path);
            if (path.lastIndexOf("/") != path.length() - 1)
                builder.append("/");
        }
        if (settings.getFilePrefix() != null)
            builder.append(settings.getFilePrefix());
        builder.append(fileName);
        return builder.toString();
    }

    public boolean createPrintWriter() {
        if (printWriter == null) {
            try {
                printWriter = new PrintWriter(new FileWriter(filePath, settings.isAppendMode()));
            } catch (IOException e) {
                if (!error) {
                    System.out.println("Ошибка при создании файла " + filePath);
                    System.out.println(e.getClass().getName() + " : " + e.getMessage());
                    error = true;
                }
                return false;
            }
        }
        return true;
    }

    public void printStats() {
        if (printWriter != null && (settings.isShortStats() || settings.isFullStats())) {
            System.out.println("Файл: " + filePath);
            System.out.println("Количество записанных элементов: " + count);
            if (settings.isFullStats() && count > 0) {
                printFullStats();
            }
            System.out.println();
        }
    }

    public abstract void printFullStats();

    public void clearFileIfExists() {
        if (Files.exists(Path.of(filePath))) {
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write("");
            } catch (IOException ignored) {
            }
        }
    }

    public void close() {
        if (printWriter != null)
            printWriter.close();
    }
}
