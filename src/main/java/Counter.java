import enumerations.EmployeeColumn;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Counter {

  private static CSVFormat csvFormat = CSVFormat.DEFAULT
          .withHeader("PatLocation")
          .withFirstRecordAsHeader()
          .withIgnoreSurroundingSpaces();

  private static CSVFormat csvFormat2 = CSVFormat.DEFAULT
          .withHeader("Name", "Count")
          .withFirstRecordAsHeader()
          .withIgnoreSurroundingSpaces();

  public static void main(String[] args) throws IOException {

    Path csvInputFilePath = Paths.get(args[0]);
    Path csvOutputFilePath = Paths.get(args[1]);
    Path csvOutputFilePath2 = Paths.get(args[2]);

    try (Reader reader = Files.newBufferedReader(csvInputFilePath);
         Writer writer = Files.newBufferedWriter(csvOutputFilePath);
         Writer writer2 = Files.newBufferedWriter(csvOutputFilePath2)) {

      CSVParser parser = new CSVParser(reader, csvFormat);
      CSVPrinter printer = new CSVPrinter(writer, csvFormat2);
      CSVPrinter printer2 = new CSVPrinter(writer2, csvFormat2);

      List<String> records = new ArrayList<>();

      Iterator<CSVRecord> recordIterator = parser.iterator();
      while (recordIterator.hasNext()) {
        CSVRecord record = recordIterator.next();
        records.add(record.get(0));
      }

      Map<String, Long> counts = records.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));

      //List<String> list = new ArrayList<String>(counts.keySet()).stream().map();

      Map<String, Long> counts2 = records.stream().map(e -> e.split("\\s")[0]).collect(Collectors.groupingBy(e -> e, Collectors.counting()));

      counts.forEach((s, l) -> {
        try {
          printer.printRecord(s, l);
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

      counts2.forEach((s, l) -> {
        try {
          printer2.printRecord(s, l);
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

      printer.flush();
      printer2.flush();
    }
  }


}
