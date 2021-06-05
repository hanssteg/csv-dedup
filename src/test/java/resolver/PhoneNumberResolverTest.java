package resolver;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PhoneNumberResolverTest {

  @Test
  public void shouldResolveJustDigitsPhone() {
    String inputPhone = "1234567890";
    String expectedOutputPhone = "1234567890";
    String actualOutputPhone = new PhoneNumberResolver().resolve(inputPhone);
    assertEquals(expectedOutputPhone, actualOutputPhone);
  }

  @Test
  public void shouldResolveDigitsAndLettersPhone() {
    String inputPhone = "1800ABcdEFG";
    String expectedOutputPhone = "1800ABCDEFG";
    String actualOutputPhone = new PhoneNumberResolver().resolve(inputPhone);
    assertEquals(expectedOutputPhone, actualOutputPhone);
  }

  @Test
  public void shouldResolvePhoneWithPlusDashParinthesesAndWhitespace() {
    String inputPhone = " +1 - (800) - abc - DEFG";
    String expectedOutputPhone = "1800ABCDEFG";
    String actualOutputPhone = new PhoneNumberResolver().resolve(inputPhone);
    assertEquals(expectedOutputPhone, actualOutputPhone);
  }
}
