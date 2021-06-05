package resolver;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmailResolverTest {

  @Test
  public void shouldResolveLowerCaseEmail() {
    String inputEmail = "someemail@test.org";
    String expectedOutputEmail = "someemail@test.org";
    String actualOutputEmail = new EmailResolver().resolve(inputEmail);
    assertEquals(expectedOutputEmail, actualOutputEmail);
  }

  @Test
  public void shouldResolveEmailWithWhitespace() {
    String inputEmail = "  someemail@test.org  ";
    String expectedOutputEmail = "someemail@test.org";
    String actualOutputEmail = new EmailResolver().resolve(inputEmail);
    assertEquals(expectedOutputEmail, actualOutputEmail);
  }

  @Test
  public void shouldResolveEmailWithCapitalLettersToLowercase() {
    String inputEmail = "SOMEeMail@tEst.orG";
    String expectedOutputEmail = "someemail@test.org";
    String actualOutputEmail = new EmailResolver().resolve(inputEmail);
    assertEquals(expectedOutputEmail, actualOutputEmail);
  }
}
