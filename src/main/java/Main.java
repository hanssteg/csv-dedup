import input.InputArguments;
import input.InputArgumentsParser;
import input.InvalidInputArgumentsException;
import output.OutputPathCreator;
import resolver.EmailResolver;
import resolver.PhoneNumberResolver;

import java.nio.file.Path;

public class Main {

  public static void main(String[] args) throws InvalidInputArgumentsException {

    try {
      InputArguments inputArguments = new InputArgumentsParser().getInputArguments(args);
      Path outputPath = new OutputPathCreator().getOutputPath(args[0]);

      EmailResolver emailResolver = new EmailResolver();
      PhoneNumberResolver phoneNumberResolver = new PhoneNumberResolver();
      EmployeeCSVDeduplicator employeeCSVDeduplicator = new EmployeeCSVDeduplicator();

      employeeCSVDeduplicator.createDeduplicatedCsv(inputArguments.getCsvFilePath(), outputPath,
              inputArguments.getDeduplicationMethod(), emailResolver, phoneNumberResolver);
    }
    catch (InvalidInputArgumentsException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }
  }
}
