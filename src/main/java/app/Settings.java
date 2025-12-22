package app;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Settings {
    private boolean appendMode;
    private boolean shortStats;
    private boolean fullStats;
    private String outputPath;
    private String filePrefix;
    private List<String> inputFileNames;
}
