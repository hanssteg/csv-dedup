package resolver;

public class EmailResolver implements Resolver {
  public String resolve(String email) {
    return email.toLowerCase().trim();
  }
}
