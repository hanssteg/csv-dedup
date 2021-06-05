import input.InputArguments;
import input.InputArgumentsParser;
import input.InvalidInputArgumentsException;
import output.OutputPathCreator;
import resolver.EmailResolver;
import resolver.PhoneNumberResolver;

import java.io.IOException;
import java.nio.file.Path;

public class Main {

  public static void main(String[] args) throws InvalidInputArgumentsException {

    try {
      InputArguments inputArguments = new InputArgumentsParser().getInputArguments(args);
      Path outputPath = new OutputPathCreator().getOutputPath(args[0]);

      EmailResolver emailResolver = new EmailResolver();
      PhoneNumberResolver phoneNumberResolver = new PhoneNumberResolver();
      EmployeeCSVDeduplicator employeeCSVDeduplicator = new EmployeeCSVDeduplicator();
      BasicPhoneEmailDuplicateChecker duplicateChecker = new BasicPhoneEmailDuplicateChecker(inputArguments.getDeduplicationMethod());

      employeeCSVDeduplicator.createDeduplicatedCsv(inputArguments.getCsvFilePath(), outputPath,
              duplicateChecker, emailResolver, phoneNumberResolver);
    }
    catch (InvalidInputArgumentsException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    } catch (IOException ex) {
      System.out.println("Unexpected IO Error Occured: " + ex.getMessage());
      ex.printStackTrace();
      System.exit(1);
    } catch (IllegalArgumentException ex) {
      System.out.println("Bad CSV Format: " + ex.getMessage());
      ex.printStackTrace();
      System.exit(1);
    }
  }
}
