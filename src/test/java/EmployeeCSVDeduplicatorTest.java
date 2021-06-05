import enumerations.DeduplicationMethod;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import resolver.EmailResolver;
import resolver.PhoneNumberResolver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeCSVDeduplicatorTest {

  @Test
  public void test() throws IOException {
    runTest("valid_input.csv", "valid_input_either_deduped.csv", DeduplicationMethod.EMAIL_OR_PHONE);
  }

  @Test
  public void test2() throws IOException {
    runTest("valid_input.csv", "valid_input_email_deduped.csv", DeduplicationMethod.EMAIL);
  }

  @Test
  public void test3() throws IOException {
    runTest("valid_input.csv", "valid_input_phone_deduped.csv", DeduplicationMethod.PHONE);
  }

  private void runTest(String inputFileName, String expectedOutputFileName, DeduplicationMethod deduplicationMethod) throws IOException {
    Path input = Paths.get(getClass().getClassLoader().getResource(inputFileName).getFile());
    File actualOutputFile = File.createTempFile(UUID.randomUUID().toString(), null);
    FileUtils.forceDeleteOnExit(actualOutputFile);

    EmployeeCSVDeduplicator employeeCSVDeduplicator = new EmployeeCSVDeduplicator();
    employeeCSVDeduplicator.createDeduplicatedCsv(input, actualOutputFile.toPath(), deduplicationMethod, new EmailResolver(), new PhoneNumberResolver());

    File expectedOutputFile = new File(getClass().getClassLoader().getResource(expectedOutputFileName).getFile());
    assertTrue(FileUtils.contentEqualsIgnoreEOL(actualOutputFile, expectedOutputFile, null));
  }
}
