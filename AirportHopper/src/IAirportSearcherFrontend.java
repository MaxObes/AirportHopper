// --== CS400 Project Three File Header ==--
// Name: Maxwell Oberbrunner
// CSL Username: oberbrunner
// Email: moberbrunner@wisc.edu
// Lecture #: <001 @11:00am>
// Notes to Grader: -

import java.util.List;

/**
 * Interface for frontend of AirportSearcher application
 * 
 * @author Maxwell Oberbrunner
 *
 */
public interface IAirportSearcherFrontend {
	
	  /**
	   * The constructor that the implementation this interface will provide. It takes the Scanner that
	   * will read user input as a parameter as well as the backend.
	   */
	  // IAirportSearcherFrontend(Scanner userInputScanner, IAirportSearcherBackend backend, IIATAValidator
	  // validator)

	  /**
	   * This method starts the command loop for the frontend, and will terminate when the user exists
	   * the app.
	   */
	  public void runCommandLoop();

	  // to help make it easier to test the functionality of this program,
	  // the following helper methods will help support runCommandLoop():

	  public void displayMainMenu(); // prints command options to System.out 

	  public void displayAirports(List<String> airports); // displays a full list of airports
	  
	  public void displayMST(); // reads maximum distance from System.in, displays results
	  
	  public void displayDistance(); // returns distance between two given airports
	  
}