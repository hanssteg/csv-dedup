import enumerations.DeduplicationMethod;

import java.util.HashSet;

public class BasicPhoneEmailDuplicateChecker implements PhoneEmailDuplicatedChecker{

  private HashSet<String> emails;
  private HashSet<String> phoneNumbers;
  private DeduplicationMethod deduplicationMethod;

  public BasicPhoneEmailDuplicateChecker(DeduplicationMethod deduplicationMethod) {
    emails = new HashSet<>();
    phoneNumbers = new HashSet<>();
    this.deduplicationMethod = deduplicationMethod;
  }

  public boolean isDuplicate(String phoneNumber, String email) {

    boolean isPhoneNumberDuplicate = phoneNumbers.contains(phoneNumber);
    boolean isEmailDuplicate = emails.contains(email);

    boolean isDuplicate = false;
    switch (deduplicationMethod) {
      case EMAIL: isDuplicate = isEmailDuplicate; break;
      case PHONE: isDuplicate = isPhoneNumberDuplicate; break;
      case EMAIL_OR_PHONE: isDuplicate = isEmailDuplicate || isPhoneNumberDuplicate; break;
    }

    //Note: only add if not duplicate, this is needed for the email_or_phone case to work
    if (!isDuplicate) {
      phoneNumbers.add(phoneNumber);
      emails.add(email);
    }

    return isDuplicate;
  }
}
