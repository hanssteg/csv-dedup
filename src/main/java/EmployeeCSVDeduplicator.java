import enumerations.EmployeeColumn;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import resolver.Resolver;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
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
   * @param duplicateChecker class that keeps track of what has been process and tells whether a row is a duplicate
   * @param emailResolver resolver to determine how emails should be resolved (i.e. make them lower case)
   * @param phoneNumberResolver class to determine how phone numbers should be resolved (i.e. only take digits)
   */
  public void createDeduplicatedCsv(Path csvInputFilePath, Path csvOutputFilePath, BasicPhoneEmailDuplicateChecker duplicateChecker,
                                    Resolver emailResolver, Resolver phoneNumberResolver) throws IOException {

    try (Reader reader = Files.newBufferedReader(csvInputFilePath);
         Writer writer = Files.newBufferedWriter(csvOutputFilePath)) {

      CSVParser parser = new CSVParser(reader, csvFormat);
      CSVPrinter printer = new CSVPrinter(writer, csvFormat);
      printer.printRecord(EmployeeColumn.FIRST_NAME.columnName, EmployeeColumn.LAST_NAME.columnName,
              EmployeeColumn.EMAIL.columnName, EmployeeColumn.PHONE.columnName);

      Iterator<CSVRecord> recordIterator = parser.iterator();
      while (recordIterator.hasNext()) {
        CSVRecord record = recordIterator.next();
        if (record.size() == parser.getHeaderNames().size()) { //Skip records with wrong number or entries
          String email = emailResolver.resolve(record.get(EmployeeColumn.EMAIL.columnName));
          String phoneNumber = phoneNumberResolver.resolve(record.get(EmployeeColumn.PHONE.columnName));

          boolean isDuplicate = duplicateChecker.isDuplicate(phoneNumber, email);

          if (!isDuplicate) {
            printer.printRecord(
                    record.get(EmployeeColumn.FIRST_NAME.columnName),
                    record.get(EmployeeColumn.LAST_NAME.columnName),
                    email,
                    phoneNumber
            );
          }
        }
      }

      printer.flush();
    }

  }
}
