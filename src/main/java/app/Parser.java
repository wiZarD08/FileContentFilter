package app;

import org.apache.commons.cli.*;

import java.util.List;

public class Parser {
    private final Options options = new Options();
    private final CommandLineParser commandLineParser = new DefaultParser();

    public Parser() {
        options.addOption("a", false, "Add mode for output");
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
            if (fileNames.isEmpty()) throw new MissingArgumentException("Missing input-files");
            Settings settings = new Settings(cl.hasOption("a"), cl.hasOption("s"), cl.hasOption("f"),
                    cl.getOptionValue("o"), cl.getOptionValue("p"), fileNames);
            return settings;
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
