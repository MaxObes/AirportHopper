import java.util.List;


/**
 * Instances of this interface implement the search and filter functionality of the Airport Searcher app
 * using hash maps.
 */
public interface IAirportSearcherBackend {

  /**
   * Adds a new airport to the backend's database and is stored in a graph internally.
   * 
   * @param airport the airport to add
   */
  public void addAirport(IAirport airport);

  /**
   * Returns a list of strings where each string is an airport that we have stored in our graph
   */
  public List<String> getAllAirports();

  /**
   * Returns the number of airports stored in the backend's database.
   * 
   * @return the number of airports
   */
  public int getNumberOfAirports();

  /**
   * Returns a formatted string containing a minimum spanning tree for the airports stored in this
   * graph
   * 
   * @return a formatted string with a MST for these airports
   */
  public String getMST(String airport);
  
  /**
   * Returns the total path cost from the source Airport to its destination
   * @param source
   * @param destination
   * @return the total distance
   */
  public double getTotalPathDistance(String source, String destination);

  /**
   * Return a string describing the shortest distance between the source node and the destination
   * node. This will check if we have puddlejumper mode on and deal appropriately with the
   * consequences of using puddlejumper mode.
   * 
   * @param source which airport we start at
   * @param source which airport we end at
   * @return a string describing the shortest distance between the source and the destination
   */
  public String getShortestDistance(String source, String destination);

}
