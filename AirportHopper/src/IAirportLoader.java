// The FileNotFoundException class is defined in the java.io package
import java.io.FileNotFoundException;

// The List interface is defined in the java.util package
import java.util.List;

/**
 * Instances of this interface can be used to load Airport data from a file that uses a subset of
 * the DOT graph description language.
 */
public interface IAirportLoader {

  /**
   * This method loads the list of Airports from a DOT file.
   * 
   * @param filepathToDOT path to the DOT file relative to the executable
   * @return a list of Airport objects
   * @throws FileNotFoundException when an attempt to open file denoted by a specified pathname has
   *                               failed
   */
  List<IAirport> loadAirports(String filepathToDOT) throws FileNotFoundException;

}

