package enumerations;

public enum EmployeeColumn {
  FIRST_NAME("FirstName"),
  LAST_NAME("LastName"),
  EMAIL("Email"),
  PHONE("Phone");

  public final String columnName;

  EmployeeColumn(String columnName) {
    this.columnName = columnName;
  }
}
