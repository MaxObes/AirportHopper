// --== CS400 File Header Information ==--
// Name: Muhammad Hareez FItri bin Ahnuar
// Email: mahnuar@wisc.edu
// Team: AA
// TA: Yuye Jiang
// Lecturer: Gary Dahl
// Notes to Grader: -

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 
 * Instances of this class implement the search and filter functionality of the Airport Searcher app
 * using hash maps.
 *
 * @author Muhammad Hareez Fitri bin Ahnuar
 */
public class AirportSearcherBackend implements IAirportSearcherBackend{
	
	// List of airports
	private List<IAirport> airportList;
	
	// Graph of connected airports
	private GraphADT<IAirport, Integer> airportSystem;
	
	// Hashtable of airport with IATA as its key
	private Hashtable<String, IAirport> airportIATA;
	
	/**
	 * Constructor that initiates all the fields
	 */
	public AirportSearcherBackend() {
		this.airportSystem = new CS400Graph_AE<>();		
		this.airportIATA = new Hashtable<>();
		this.airportList = new ArrayList<IAirport>();
	}
	
	/**
	 * Adds a new airport to the backend's database and is stored in a graph
	 * internally.
	 * 
	 * @param airport the airport to add
	 */
	@Override
	public void addAirport(IAirport airport) {
		if (airport == null) {
			return;
		}

		if (airportIATA.containsKey(airport.getIATA())) {
			return;
		}

		boolean added = airportSystem.insertVertex(airport);
		if (added) {
			airportList.add(airport); // add to array list
			airportIATA.put(airport.getIATA(), airport); // add to hashtable
		}

		if (airportList.size() > 1) {
			populateGraph();
		}

	}
	
	/**
	 * Adds edges from and to an airport with its corresponding distance
	 * 
	 * @param sourceIATA
	 * @param destinationIATA
	 * @param distance
	 * @return
	 */
	private boolean addFlight(String sourceIATA, String destinationIATA, int distance) {
		// if the source and destination airports don't exist, return false since flight is not added
	    if (!airportIATA.containsKey(sourceIATA) || !airportIATA.containsKey(destinationIATA))
	      return false;

	    // inserting an edge returns a boolean, so that will be returned to the frontend as well to be
	    // accurate about
	    // whether the flight was actually added or not
	    return airportSystem.insertEdge(airportIATA.get(sourceIATA), airportIATA.get(destinationIATA),
	        distance);
    }
	
	/**
	 * Method to create the graph into an airport system
	 */
	private void populateGraph() {
		// connect airports using flight data (this is done separately from the
		// previous step to avoid null pointer exceptions of connecting a
		// non-existent/null airport)
		for (IAirport airport : airportList) {

			// for each airport in the list of airports, get the list of origin airports to
			// for loop
			// through
			List<String> destinationIATAs = airport.destinationsIATA();
			// this for loop goes through each 'flight' for each airport and creates a new
			// edge in the
			// graph for it
			for (int i = 0; i < destinationIATAs.size(); i++) {
				String destIATA = destinationIATAs.get(i);
				addFlight(airport.getIATA(), destIATA, airport.distances().get(i));
			}
		}
	}

	/**
	 * Returns a list of strings where each string is an airport that we have stored in our graph
	 */		
	@Override
	public List<String> getAllAirports() {
		List<String> allAirports = new ArrayList<String>();
		for (IAirport airport : this.airportList) {
			allAirports.add(airport.toString());
		}
		return allAirports;
	}

	/**
	 * Returns the number of airports stored in the backend's database.
	 * 
	 * @return the number of airports
	 */
	@Override
	public int getNumberOfAirports() {
		return this.airportList.size();
	}

	/**
	 * Returns a formatted string containing a minimum spanning tree for the airports stored in this
	 * graph
	 * 
	 * @return a formatted string with a MST for these airports
	 */
	@Override
	public String getMST(String airport) {
		String currNodeVal = airport.toUpperCase();
		CS400Graph_AE<IAirport, Integer>.MST currMST = ((CS400Graph_AE<IAirport, Integer>) this.airportSystem).generateMST(airportIATA.get(currNodeVal));		
		String result = currMST.toString();

		return result;
	}
	
	/**
	 * Return a string describing the shortest distance between the source node and the destination
	 * node. 
	 * 
	 * @param source which airport we start at
	 * @param source which airport we end at
	 * @return a string describing the shortest distance between the source and the destination
	 */
	@Override
	public String getShortestDistance(String source, String destination) throws NoSuchElementException{
		
		String sourceAirport = source.toUpperCase();
		String destAirport = destination.toUpperCase();
		
		if (!airportIATA.containsKey(sourceAirport) || !airportIATA.containsKey(destAirport))
            throw new NoSuchElementException("Provided origin or destination airport do not exist");
		
		String shortestPath = "";
		List<IAirport> listOfShortestPath = airportSystem.shortestPath(airportIATA.get(sourceAirport), airportIATA.get(destAirport));
		for (int i = 1 ; i < listOfShortestPath.size() ; i++) {
		    shortestPath += " - - > " + listOfShortestPath.get(i).getIATA();
		}
		return airportIATA.get(sourceAirport).getIATA() + shortestPath;
	}

    @Override
    public double getTotalPathDistance(String source, String destination) {
	String sourceAirport = source.toUpperCase();
	String destAirport = destination.toUpperCase();
		
	if (!airportIATA.containsKey(sourceAirport) || !airportIATA.containsKey(destAirport))
            throw new NoSuchElementException("Provided origin or destination airport do not exist");
		
	return airportSystem.getPathCost(airportIATA.get(sourceAirport), airportIATA.get(destAirport));
    }
}
