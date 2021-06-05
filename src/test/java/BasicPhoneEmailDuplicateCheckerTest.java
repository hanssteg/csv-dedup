import enumerations.DeduplicationMethod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicPhoneEmailDuplicateCheckerTest {

  @Test
  public void shouldIdentifyPhoneDuplicates() {
    BasicPhoneEmailDuplicateChecker duplicateChecker = new BasicPhoneEmailDuplicateChecker(DeduplicationMethod.PHONE);

    String email = "...";
    String phoneOne = "1234567890";
    String phoneTwo = "0123456789";

    assertFalse(duplicateChecker.isDuplicate(phoneOne, email));
    assertTrue(duplicateChecker.isDuplicate(phoneOne, email));
    assertFalse(duplicateChecker.isDuplicate(phoneTwo, email));
    assertTrue(duplicateChecker.isDuplicate(phoneTwo, email));
    assertTrue(duplicateChecker.isDuplicate(phoneOne, email));
    assertTrue(duplicateChecker.isDuplicate(phoneTwo, email));
  }

  @Test
  public void shouldIdentifyEmailDuplicates() {
    BasicPhoneEmailDuplicateChecker duplicateChecker = new BasicPhoneEmailDuplicateChecker(DeduplicationMethod.EMAIL);


    String phone = "1234567890";
    String emailOne = "one@fake.org";
    String emailTwo = "two@fake.org";

    assertFalse(duplicateChecker.isDuplicate(phone, emailOne));
    assertTrue(duplicateChecker.isDuplicate(phone, emailOne));
    assertFalse(duplicateChecker.isDuplicate(phone, emailTwo));
    assertTrue(duplicateChecker.isDuplicate(phone, emailTwo));
    assertTrue(duplicateChecker.isDuplicate(phone, emailOne));
    assertTrue(duplicateChecker.isDuplicate(phone, emailTwo));
  }

  @Test
  public void shouldIdentifyEmailORPhoneDuplicates() {
    BasicPhoneEmailDuplicateChecker duplicateChecker = new BasicPhoneEmailDuplicateChecker(DeduplicationMethod.EMAIL_OR_PHONE);

    String emailOne = "one@fake.org";
    String emailTwo = "two@fake.org";
    String phoneOne = "1234567890";
    String phoneTwo = "0123456789";

    assertFalse(duplicateChecker.isDuplicate(phoneOne, emailOne));
    assertTrue(duplicateChecker.isDuplicate(phoneOne, emailTwo));
    assertTrue(duplicateChecker.isDuplicate(phoneTwo, emailOne));
    assertFalse(duplicateChecker.isDuplicate(phoneTwo, emailTwo));
    assertTrue(duplicateChecker.isDuplicate(phoneOne, emailOne));
    assertTrue(duplicateChecker.isDuplicate(phoneOne, emailTwo));
    assertTrue(duplicateChecker.isDuplicate(phoneTwo, emailOne));
    assertTrue(duplicateChecker.isDuplicate(phoneTwo, emailTwo));
  }
}
