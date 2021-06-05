package resolver;

public class PhoneNumberResolver implements Resolver{
  public String resolve(String phoneNumber) {
    return phoneNumber.replaceAll("[\\(\\)\\+\\-\\s]", "").toUpperCase();
  }
}
