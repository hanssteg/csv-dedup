import enumerations.DeduplicationMethod;

import java.util.HashSet;

public class DuplicateChecker {

  private HashSet<String> emails;
  private HashSet<String> phoneNumbers;
  private DeduplicationMethod deduplicationMethod;

  public DuplicateChecker(DeduplicationMethod deduplicationMethod) {
    emails = new HashSet<>();
    phoneNumbers = new HashSet<>();
    this.deduplicationMethod = deduplicationMethod;
  }

  public boolean isDuplicate(String phoneNumber, String email) {

    boolean isPhoneNumberDuplicate = !phoneNumbers.add(phoneNumber);
    boolean isEmailDuplicate = !emails.add(email);

    boolean isDuplicate = false;
    switch (deduplicationMethod) {
      case EMAIL: isDuplicate = isEmailDuplicate; break;
      case PHONE: isDuplicate = isPhoneNumberDuplicate; break;
      case EMAIL_OR_PHONE: isDuplicate = isEmailDuplicate || isPhoneNumberDuplicate; break;
    }

    return isDuplicate;
  }
}
