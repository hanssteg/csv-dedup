package output;

import java.nio.file.Path;
import java.nio.file.Paths;

public class OutputPathCreator {
  public Path getOutputPath(String inputFilePath) {
    return Paths.get(inputFilePath.substring(0, inputFilePath.length() - 4) + "_deduped.csv");
  }
}
