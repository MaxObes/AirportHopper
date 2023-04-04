// The List interface is defined in the java.util package
import java.util.List;

/**
 * This interface defines getter methods for each airport's data attributes and is implemented by
 * classes that represent an Airport and its associated data.
 */
public interface IAirport {

  /**
   * Returns the 3-letter IATA code of the Airport.
   * 
   * @return 3-letter IATA code of the Airport
   */
  String getIATA();

  /**
   * Returns the name of the Airport.
   * 
   * @return name of the Airport
   */
  String getName();

  /**
   * Returns the list of distances (edge weight) from the source Airport.
   * 
   * @return list of distances from the source Airport
   */
  List<Integer> distances();

  /**
   * Returns the list of all the IATA codes of the destination Airport from the source Airport.
   * 
   * @return list of all the IATA codes of the destination Airport from the source Airport
   */
  List<String> destinationsIATA();

  /**
   * Returns the list of all the names of the destination Airport from the source Airport.
   * 
   * @return list of all the names of the destination Airport from the source Airport
   */
  List<String> destinationsList();

  /**
   * A string representation of the Airport object (Used mainly for testing and debugging)
   * 
   * @return string representation of the Airport object
   */
  @Override
  String toString();

}
