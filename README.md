# CSV Clean Up

##Problem Statement

The Kevala platform includes some basic employee management functionality. When we
onboard a new company we bulk upload their existing employees into our system using a
simple CSV import process. Unfortunately the data we get from customers is not always perfect,
one of the issues we run into is duplicate rows representing the same employee. We would like
you to write a program that can take a CSV input file, identify and remove duplicate rows.

We identify duplicates as rows that either...
1. ... have the same email address or
2. ... have the same phone number

... however we're not yet convinced about the 2nd rule - it's possible 2 distinct employees might
share the same phone number. So we would like to be able to run this program with various
different strategies, e.g
- email - identify duplicates based on matching email only
- phone - identify duplicates based on matching phone only
- email_or_phone - identify duplicates based on matching either email or phone

INPUT: There should be 2 inputs to the program, (a) the csv file and (b) which duplicate
detection strategy is to be used. The CSV file can be assumed to contain a header row with the
following columns [ FirstName, LastName, Email, Phone ]. The values for some columns may
be missing.

OUTPUT: The output should be a new CSV where duplicate rows have been removed based on
the specified detection strategy

Guidelines
- The exercise should only take a few hours to complete.
- Please use your preferred programming language, anything that can be compiled and
tested on OSX or Linux will be fine - but include install instructions if necessary.
- We are looking for the ability to solve the problem thoughtfully, code readability, test
coverage, and clear communication. Please ensure the program is well tested,
automated testing is very important to us.
- We use Elixir, Ruby, or Javascript, but we also believe that programming languages can
be learned so feel free to use your preferred language of choice.
- Try to handle unexpected CSV inputs as gracefully as possible.
- Send your solution (link to a public Github repo, or a zip archive) to jake@kevala.care

## Assumptions

- The interface for the app will be the command line
- The output file with have the same name as the input with `_deduped` added to the end
- The input file is a valid CSV with 4 columns ([ FirstName, LastName, Email, Phone ])
  - If a row of data has more or less than 4 values then it will be excluded from the output and not used to dedup
- Whitespace will be trimmed from all values before processing
- When checking email addresses, case will be ignored
- When checking phone numbers, `(`,`)`,`-`,`+`, and whitespace will be ignored. The output will just have numbers
    - Other thoughts
      - Could ignore all non numeric characters, but letters can possibly be valid
      - Could also remove 1 from the start of a number if it is 11 digits long (or other codes if we go international)
- When writing output trimmed whitespace, adjusted phone numbers, and lowercase emails will be used~~~~
- No validation or filtering will be done to detect or clean up invalid formats for email addresses or phone numbers
  - i.e if you enter email `bademail!` and phone `?badphonenum`, those will end up in the output
- In the case of a duplicate, the row to keep and the row to delete will be random
  - i.e. if the duplicate rows have different first and last name, one first/last name will be dropped
- In certain cases an error can be returned without processing the file
  - If the input file extension is not `.csv`
  - If the input file header column is does not have exactly 4 hearders: [ FirstName, LastName, Email, Phone ]
  - If the input file cannot be found
- The size of the input file will be small enough to keep in memory (say under 1GB)
  - if the encoding us US-ASCII each character take 1 byte, if the average row is 50 characters and the biggest company in the world had 1.5 million employees, that file would take a little over 75MB, so we should be good
- The input file will be UTF-8 and have US ASCII characters

## How to Run

- This assumes you have Jave installed, I build this running with Java 11.0

### Option 1 - use Maven
- Make sure you have Maven installed `mvn -version` (if you need to install look here http://maven.apache.org/install.html)
- After cloning the git Repo
    - Run `mvn clean verify` in the root directory of the project, this runs the tests and creates a jar
    - A jar will be created in at `target/csv-dedup-1.0-SNAPSHOT.jar`
    - Run `java -jar /target/csv-dedup-1.0-SNAPSHOT-jar-with-dependencies.jar pathToCsvFile deduplicationMethod`
    - The options for deduplication method are `email`, `phone`, and `email_or_phone`
    
### Option 2 - Use the jar provided in the repo if you do not want to set up Maven
- There is a jar file at `jar/csv-dedup.jar`
- There are sample inputs in `/jar/samples/`
- From the `/jar` directory, execute the command `java -jar csv-dedup.jar samples/sample.csv email_or_phone` as a an example 
