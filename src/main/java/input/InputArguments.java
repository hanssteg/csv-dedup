package input;

import enumerations.DeduplicationMethod;

import java.nio.file.Path;

public class InputArguments {
  private Path csvFilePath;
  private DeduplicationMethod deduplicationMethod;

  public InputArguments(Path csvFilePath, DeduplicationMethod deduplicationMethod) {
    this.csvFilePath = csvFilePath;
    this.deduplicationMethod = deduplicationMethod;
  }

  public Path getCsvFilePath() {
    return csvFilePath;
  }

  public DeduplicationMethod getDeduplicationMethod() {
    return deduplicationMethod;
  }
}
