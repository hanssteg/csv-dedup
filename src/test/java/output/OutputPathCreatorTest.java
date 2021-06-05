package output;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputPathCreatorTest {

  @Test
  public void shouldCreateCorrectOutputPath() {
    String inputPath = "some/Path.csv";
    String expectedOutputPath = "some/Path_deduped.csv";
    String actualOutputPath = new OutputPathCreator().getOutputPath(inputPath).toString();
    assertEquals(expectedOutputPath, actualOutputPath);
  }
}
