package input;

import enumerations.DeduplicationMethod;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InputArgumentsParserTest {

  @Test
  public void shouldParseValidInputsCorrectly() throws InvalidInputArgumentsException {

    String expectedPathString = getClass().getClassLoader().getResource("valid_input.csv").getFile();
    Path expectedPath = Paths.get(expectedPathString);
    DeduplicationMethod expectedDeduplicationMethed = DeduplicationMethod.EMAIL;

    InputArgumentsParser inputArgumentsParser = new InputArgumentsParser();
    InputArguments actualArguments = inputArgumentsParser.getInputArguments(new String[] {expectedPathString, "email"});

    assertEquals(expectedDeduplicationMethed, actualArguments.getDeduplicationMethod());
    assertEquals(expectedPath, actualArguments.getCsvFilePath());

    actualArguments = inputArgumentsParser.getInputArguments(new String[] {expectedPathString, "eMaiL"});

    assertEquals(expectedDeduplicationMethed, actualArguments.getDeduplicationMethod());
    assertEquals(expectedPath, actualArguments.getCsvFilePath());
  }

  @Test
  public void shouldErrorWithLessThanTwoArguments() {
    InputArgumentsParser inputArgumentsParser = new InputArgumentsParser();
    assertThrows(InvalidInputArgumentsException.class ,() -> inputArgumentsParser.getInputArguments(new String[] {}));
    assertThrows(InvalidInputArgumentsException.class ,() -> inputArgumentsParser.getInputArguments(new String[] {"one"}));
  }

  @Test
  public void shouldErrorWithMoreThanTwoArguments() {
    InputArgumentsParser inputArgumentsParser = new InputArgumentsParser();
    assertThrows(InvalidInputArgumentsException.class ,() -> inputArgumentsParser.getInputArguments(new String[] {"1","2","3"}));
    assertThrows(InvalidInputArgumentsException.class ,() -> inputArgumentsParser.getInputArguments(new String[] {"1","2","3","4"}));
  }

  @Test
  public void shouldErrorIfFirstArgIsNotCSV() {
    InputArgumentsParser inputArgumentsParser = new InputArgumentsParser();
    assertThrows(InvalidInputArgumentsException.class ,() -> inputArgumentsParser.getInputArguments(new String[] {"someFile", "email"}));
  }

  @Test
  public void shouldErrorIfFirstArgIsCSVThatDoesNotExist() {
    InputArgumentsParser inputArgumentsParser = new InputArgumentsParser();
    assertThrows(InvalidInputArgumentsException.class ,() -> inputArgumentsParser.getInputArguments(new String[] {"someFile.csv", "email"}));
  }

  @Test
  public void shouldErrorIfSecondArgumentIsInvalidDeduplicationMethod() {
    InputArgumentsParser inputArgumentsParser = new InputArgumentsParser();

    String expectedPathString = getClass().getClassLoader().getResource("valid_input.csv").getFile();
    Path expectedPath = Paths.get(expectedPathString);

    assertThrows(InvalidInputArgumentsException.class ,() -> inputArgumentsParser.getInputArguments(new String[] {expectedPathString, "bad"}));
  }
}
