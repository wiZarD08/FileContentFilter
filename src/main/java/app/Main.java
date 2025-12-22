package app;

public class Main {
    public static void main(String[] args) {
        Settings settings = new Parser().parse(args);
        if (settings != null) {
            new FileContentFilter(settings).filter();
        }
    }
}
