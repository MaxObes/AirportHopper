// --== CS400 File Header Information ==--
// Name: Faris Hazim Mohamed Zaimir
// Email: mohamedzaimi@wisc.edu
// Team: AA
// TA: Yuye Jiang
// Lecturer: Gary Dahl
// Notes to Grader: -

// The List interface is defined in the java.util package
import java.util.List;

/**
 * This class implements the IAirport interface to represent an Airport object with its associated
 * data.
 * 
 * @author Faris Hazim Mohamed Zaimir
 */
public class Airport implements IAirport {

  private String airportName;
  private String airportIATA;
  private List<Integer> destinationDistances;
  private List<String> destinationNames;
  private List<String> destinationIATA;

  /**
   * A constructor for an Airport along with its name, 3-letter IATA code, distances from its
   * destination airports, names of its destination airports, and the 3-letter IATA code of its
   * destination airports.
   * 
   * @param airportName          the name of the Airport
   * @param airportIATA          the 3-letter IATA code of the Airport
   * @param destinationDistances the list of distances from the Airport to its destinations
   * @param destinationNames     the list of names of the Airport this Airport travels to
   * @param destinationIATA      the list of 3-letter IATA codes of the Airport this Airport travels
   *                             to
   */
  public Airport(String airportName, String airportIATA, List<Integer> destinationDistances,
      List<String> destinationNames, List<String> destinationIATA) {

    this.airportName = airportName;
    this.airportIATA = airportIATA;
    this.destinationDistances = destinationDistances;
    this.destinationNames = destinationNames;
    this.destinationIATA = destinationIATA;
  }

  /**
   * Returns the 3-letter IATA code of the Airport.
   * 
   * @return 3-letter IATA code of the Airport
   * @see IAirport#getIATA()
   */
  @Override
  public String getIATA() {
    return this.airportIATA;
  }

  /**
   * Returns the name of the Airport.
   * 
   * @return name of the Airport
   * @see IAirport#getName()
   */
  @Override
  public String getName() {
    return this.airportName;
  }

  /**
   * Returns the list of distances (edge weight) from the source Airport.
   * 
   * @return list of distances from the source Airport
   * @see IAirport#distances()
   */
  @Override
  public List<Integer> distances() {
    return this.destinationDistances;
  }

  /**
   * Returns the list of all the IATA codes of the destination Airport from the source Airport.
   * 
   * @return list of all the IATA codes of the destination Airport from the source Airport
   * @see IAirport#destinationsIATA()
   */
  @Override
  public List<String> destinationsIATA() {
    return this.destinationIATA;
  }

  /**
   * Returns the list of all the names of the destination Airport from the source Airport.
   * 
   * @return list of all the names of the destination Airport from the source Airport
   * @see IAirport#destinationsList()
   */
  @Override
  public List<String> destinationsList() {
    return this.destinationNames;
  }

  /**
   * A string representation of the Airport object (Used mainly for testing and debugging)
   * 
   * @return string representation of the Airport object
   */
  @Override
  public String toString() {
    return this.airportName + " (" + this.airportIATA + ")";
  }
}
