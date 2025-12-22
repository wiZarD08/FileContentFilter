package app;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public abstract class DataWriter {
    protected final Settings settings;
    protected final String fileName;
    protected PrintWriter printWriter;
    protected long count;
    private boolean error;

    protected DataWriter(Settings settings, String fileName) {
        this.settings = settings;
        this.fileName = fileName;
    }

    public String getFilePath() {
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

    protected boolean createPrintWriter() {
        if (printWriter == null) {
            String path = getFilePath();
            try {
                printWriter = new PrintWriter(new FileWriter(path, settings.isAppendMode()));
            } catch (IOException e) {
                if (!error) {
                    System.out.println("Ошибка при создании файла " + path);
                    System.out.println(e.getClass().getName() + " : " + e.getMessage());
                    error = true;
                }
                return false;
            }
        }
        return true;
    }

    public void close() {
        if (printWriter != null)
            printWriter.close();
    }
}
