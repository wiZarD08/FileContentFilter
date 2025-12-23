package app;

import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

    public Settings parse(String[] args) throws ParseException {
        CommandLine cl = commandLineParser.parse(options, args);
        if (cl.hasOption("h")) {
            new HelpFormatter().printHelp("java -jar util.jar [OPTIONS] <input-files...>", options);
        }
        validateArguments(cl);
        return new Settings(cl.hasOption("a"), cl.hasOption("s"), cl.hasOption("f"),
                cl.getOptionValue("o"), cl.getOptionValue("p"), cl.getArgList());
    }

    private void validateArguments(CommandLine cl) throws MissingArgumentException {
        if (cl.getArgList().isEmpty())
            throw new MissingArgumentException("Не найдены имена файлов с входными данными");
        if (cl.hasOption("o") && !Files.isDirectory(Path.of(cl.getOptionValue('o')))) {
            throw new IllegalArgumentException("Директории '" + cl.getOptionValue('o') + "' не существует");
        }
        if (cl.hasOption("p") && !filePrefixIsValid(cl.getOptionValue('p'))) {
            throw new IllegalArgumentException("Не получается создать файл с префиксом '" +
                    cl.getOptionValue('p') + "'");
        }
    }

    private boolean filePrefixIsValid(String prefix) {
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
