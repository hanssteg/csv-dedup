import enumerations.DeduplicationMethod;
import enumerations.EmployeeColumn;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import resolver.EmailResolver;
import resolver.PhoneNumberResolver;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Iterator;

public class EmployeeCSVDeduplicator {

  private CSVFormat csvFormat = CSVFormat.DEFAULT
          .withHeader(EmployeeColumn.FIRST_NAME.columnName, EmployeeColumn.LAST_NAME.columnName,
                  EmployeeColumn.EMAIL.columnName, EmployeeColumn.PHONE.columnName)
          .withFirstRecordAsHeader()
          .withIgnoreSurroundingSpaces();

  /**
   * Reads the input file and writes a resolved and deduplicated version of it to the output path
   * @param csvInputFilePath path to an input CSV file
   * @param csvOutputFilePath path where an output file should be written
   * @param deduplicationMethod method to be used for deduplication
   * @param emailResolver resolver to determine how emails should be resolved (i.e. make them lower case)
   * @param phoneNumberResolver class to determine how phone numbers should be resolved (i.e. only take digits)
   */
  public void createDeduplicatedCsv(Path csvInputFilePath, Path csvOutputFilePath, DeduplicationMethod deduplicationMethod,
                                    EmailResolver emailResolver, PhoneNumberResolver phoneNumberResolver) {

    try (Reader reader = Files.newBufferedReader(csvInputFilePath);
         Writer writer = Files.newBufferedWriter(csvOutputFilePath)) {

      CSVParser parser = new CSVParser(reader, csvFormat);
      CSVPrinter printer = new CSVPrinter(writer, csvFormat);
      printer.printRecord(EmployeeColumn.FIRST_NAME.columnName, EmployeeColumn.LAST_NAME.columnName,
              EmployeeColumn.EMAIL.columnName, EmployeeColumn.PHONE.columnName);

      HashSet<String> emails = new HashSet<>();
      HashSet<String> phoneNumbers = new HashSet<>();

      Iterator<CSVRecord> recordIterator = parser.iterator();
      while (recordIterator.hasNext()) {
        CSVRecord record = recordIterator.next();
        String email = emailResolver.resolverEmailString(record.get(EmployeeColumn.EMAIL.columnName));
        String phoneNumber = phoneNumberResolver.resolvePhoneNumber(record.get(EmployeeColumn.PHONE.columnName));

        boolean phoneNumberUsed = phoneNumbers.contains(phoneNumber);
        boolean emailUsed = emails.contains(email);

        boolean isDuplicate = false;
        switch (deduplicationMethod) {
          case EMAIL: isDuplicate = emailUsed; break;
          case PHONE: isDuplicate = phoneNumberUsed; break;
          case EMAIL_OR_PHONE: isDuplicate = emailUsed || phoneNumberUsed; break;
        }

        if (!isDuplicate) {
          phoneNumbers.add(phoneNumber);
          emails.add(email);
          printer.printRecord(
                  record.get(EmployeeColumn.FIRST_NAME.columnName),
                  record.get(EmployeeColumn.LAST_NAME.columnName),
                  email,
                  phoneNumber
          );
        }
      }

      printer.flush();

    } catch (IOException ex) {
      System.out.println("Unexpected IO Error Occured: " + ex.getMessage());
      ex.printStackTrace();
      System.exit(1);
    }

  }
}
