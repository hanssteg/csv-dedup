package resolver;

public class PhoneNumberResolver {

  public String resolvePhoneNumber(String phoneNumber) {
    return phoneNumber.replaceAll("[\\(\\)\\+\\-\\s]", "");
  }
}
