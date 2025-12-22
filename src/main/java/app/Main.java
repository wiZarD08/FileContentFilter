package app;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        Settings settings = parser.parse(args);
        if (settings != null) {
            new FileContentFilter(settings).filter();
        }
    }
}
