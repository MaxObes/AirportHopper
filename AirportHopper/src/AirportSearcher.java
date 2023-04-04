import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Class with main method to run the Airport Searcher app.
 * 
 * @author 
 */
public class AirportSearcher {

  /**
   * Main method for running the application
   * 
   * @param args unused
   * @throws FileNotFoundException thrown if bad input file
   */
  public static void main(String[] args) {
    IAirportLoader airportLoader = new AirportLoader();

    // load the airports from the data file
    String csv_filename = "airports.dot";
    try {
      List<IAirport> airportList = airportLoader.loadAirports(csv_filename);

      // instantiate the backend
      IAirportSearcherBackend backend = new AirportSearcherBackend();

      // add all the airports to the backend

      for (IAirport airport : airportList) {
        backend.addAirport(airport);
      }

      // instantiate the IATA validator
    //  IIATAValidator IATAValidator = new IATAValidator();

      // instantiate the scanner for user input
      Scanner userInputScanner = new Scanner(System.in);

      // instantiate the front end and pass references to the scanner, backend, and IATA validator to it
      IAirportSearcherFrontend frontend =
          new AirportSearcherFrontend(userInputScanner, backend);

      // start the input loop of the front end
      frontend.runCommandLoop();
    } catch (FileNotFoundException e1) {
      System.out.println("Unable to find " + csv_filename + ".");
      return;
    }
  }
}
