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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmployeeCSVDeduplicatorIntegrationTest {

  @Test
  public void shouldCorrectlyProcessEmailOrPhoneDedup() throws IOException {
    runTest("valid_input.csv", "valid_input_either_deduped.csv", DeduplicationMethod.EMAIL_OR_PHONE);
  }

  @Test
  public void shouldCorrectlyProcessEmailDedup() throws IOException {
    runTest("valid_input.csv", "valid_input_email_deduped.csv", DeduplicationMethod.EMAIL);
  }

  @Test
  public void shouldCorrectlyProcessPhoneDedup() throws IOException {
    runTest("valid_input.csv", "valid_input_phone_deduped.csv", DeduplicationMethod.PHONE);
  }

  @Test
  public void csvWithWrongHeadersThrowsException() throws IOException {
    assertThrows(
            IllegalArgumentException.class,
            () -> runTest("wrong_headers.csv", "valid_input_email_deduped.csv", DeduplicationMethod.EMAIL)
    );
  }

  @Test
  public void shouldCorrectlySkipRowsThatAreTooShort() throws IOException {
    runTest("row_too_short.csv", "valid_input_either_deduped.csv", DeduplicationMethod.EMAIL_OR_PHONE);
  }

  @Test
  public void shouldCorrectlySkipRowsThatAreTooLong() throws IOException {
    runTest("row_too_long.csv", "valid_input_either_deduped.csv", DeduplicationMethod.EMAIL_OR_PHONE);
  }

  @Test
  public void shouldSuccessfullyProcessLargerFileWithManyDuplicates() throws IOException {
    runTest("valid_big_input.csv", "valid_big_input_deduped.csv", DeduplicationMethod.EMAIL_OR_PHONE);
  }

  private void runTest(String inputFileName, String expectedOutputFileName, DeduplicationMethod deduplicationMethod) throws IOException {
    Path input = Paths.get(getClass().getClassLoader().getResource(inputFileName).getFile());
    File actualOutputFile = File.createTempFile(UUID.randomUUID().toString(), null);
    FileUtils.forceDeleteOnExit(actualOutputFile);

    EmployeeCSVDeduplicator employeeCSVDeduplicator = new EmployeeCSVDeduplicator();
    BasicPhoneEmailDuplicateChecker duplicateChecker = new BasicPhoneEmailDuplicateChecker(deduplicationMethod);
    employeeCSVDeduplicator.createDeduplicatedCsv(input, actualOutputFile.toPath(), duplicateChecker, new EmailResolver(), new PhoneNumberResolver());

    File expectedOutputFile = new File(getClass().getClassLoader().getResource(expectedOutputFileName).getFile());
    assertTrue(FileUtils.contentEqualsIgnoreEOL(actualOutputFile, expectedOutputFile, null));
  }
}
