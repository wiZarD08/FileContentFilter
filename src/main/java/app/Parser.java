package app;

import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Parser {
    private final Options options = new Options();
    private final CommandLineParser commandLineParser = new DefaultParser();

    public Parser() {
        options.addOption("a", false, "Append mode for output");
        options.addOption("s", false, "Enable short statistics");
        options.addOption("f", false, "Enable full statistics");

        options.addOption(Option.builder("o")
                .hasArg()
                .desc("Set the output path")
                .build());

        options.addOption(Option.builder("p")
                .hasArg()
                .desc("Set the prefix to the output files")
                .build());

        options.addOption("h", "help", false, "Display help information");
    }

    public Settings parse(String[] args) {
        try {
            CommandLine cl = commandLineParser.parse(options, args);
            if (cl.hasOption("h")) {
                new HelpFormatter().printHelp("java -jar util.jar [OPTIONS] <input-files...>", options);
            }
            List<String> fileNames = cl.getArgList();
            if (fileNames.isEmpty()) throw new MissingArgumentException("Введите имена файлов с входными данными");
            Settings settings = new Settings(cl.hasOption("a"), cl.hasOption("s"), cl.hasOption("f"),
                    cl.getOptionValue("o"), cl.getOptionValue("p"), fileNames);

            if (settings.getOutputPath() != null && !Files.isDirectory(Path.of(settings.getOutputPath()))) {
                System.out.println("Директории '" + settings.getOutputPath() + "' не существует");
                return null;
            }
            if (settings.getFilePrefix() != null && !filePrefixIsValid(settings.getFilePrefix())) {
                System.out.println("Не получается создать файл с префиксом '" + settings.getFilePrefix() + "'");
                return null;
            }
            return settings;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static boolean filePrefixIsValid(String prefix) {
        if (prefix.indexOf('/') != -1) return false;

        File file = new File(prefix + "temp.txt");
        boolean created = false;
        try {
            created = file.createNewFile();
        } catch (IOException e) {
            return false;
        } finally {
            if (created) {
                file.deleteOnExit();
            }
        }
        return created;
    }
}
