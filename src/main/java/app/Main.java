package app;

import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;

import java.util.Arrays;

public class Main {
    private static Settings getSettingsFromArgs(String[] args) {
        Settings settings = null;
        while (settings == null) {
            try {
                settings = new Parser().parse(args);
            } catch (UnrecognizedOptionException e) {
                System.out.println("Опция " + e.getOption() + " не распознана");
                args = Arrays.stream(args).filter(x -> !x.equals(e.getOption())).toArray(String[]::new);
            } catch (ParseException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        return settings;
    }

    public static void main(String[] args) {
        Settings settings = getSettingsFromArgs(args);
        if (settings != null) {
            FileContentFilter fileContentFilter = new FileContentFilter(settings);
            fileContentFilter.filter();
            fileContentFilter.printStatsAndClose();
        } else {
            System.out.println("Программа приостановленна");
        }
    }
}
