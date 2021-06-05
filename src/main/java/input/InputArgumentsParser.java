package input;

import enumerations.DeduplicationMethod;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputArgumentsParser {

  public InputArguments getInputArguments(String[] args) throws InvalidInputArgumentsException {

    if (args.length != 2) {
      throw new InvalidInputArgumentsException(String.format("Error, expects 2 arguments and found %d\n" +
              "Usage: csvdedup.jar pathToCsv dedupMethod", args.length));
    }

    List<String> errors = new ArrayList<>();

    Path inputPath = Paths.get(args[0]);
    if (!args[0].endsWith(".csv")) {
      errors.add(String.format("Error, input file %s is not a csv file", args[0]));
    }
    else if (Files.notExists(inputPath)) {
      errors.add(String.format("Error, input file %s does not exist", args[0]));
    }

    DeduplicationMethod deduplicationMethod = null;
    try {
      deduplicationMethod = DeduplicationMethod.valueOf(args[1].toUpperCase());
    }
    catch (IllegalArgumentException e) {
      errors.add(String.format("Error, invalid deduplication method: %s. Valid options are %s", args[1],
              Arrays.asList(DeduplicationMethod.values()).stream().map(m -> m.toString()).collect(Collectors.joining(", "))));
    }

    if(!errors.isEmpty()) {
      throw new InvalidInputArgumentsException(String.format(errors.stream().collect(Collectors.joining("\n"))));
    }

    return new InputArguments(inputPath, deduplicationMethod);
  }
}
