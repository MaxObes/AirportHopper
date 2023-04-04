// --== CS400 Project Three File Header ==--
// Name: Maxwell Oberbrunner
// CSL Username: oberbrunner
// Email: moberbrunner@wisc.edu
// Lecture #: <001 @11:00am>
// Notes to Grader: -

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class utilizes TextUITester.java to convert the console output to a String
 * to test AirportSearcherFrontend.java
 * 
 * @author Maxwell Oberbrunner
 *
 */
public class FrontendDeveloperTests {
	
	TextUITester test;
	
	/**
	 * Tests that getMST() works with frontend and backend
	 */
	@Test
	void test1() {
		test = new TextUITester("1\nORD");
		
		TextUITester.run();
		
		String output = test.checkOutput();
		
		assertTrue(output.startsWith("=============================\n") &&
				output.endsWith("ORD -> ATW -> SHV\n"));
	}
	
	/**
	 * Tests that displayMainMenu exit works with frontend
	 */
	@Test
	void test2() {
		test = new TextUITester("4\n");
		
		TextUITester.run();
		
		String output = test.checkOutput();
		
		assertTrue(output.startsWith("=============================\n") &&
				output.endsWith("Goodbye!\n"));
	}
	
	/**
	 * Tests that display airports works with frontend 
	 */
	@Test
	void test3() {
		test = new TextUITester("2\n");
		
		TextUITester.run();
		
		String output = test.checkOutput();
		
		
		assertTrue(output.startsWith("=============================\n") &&
				output.contains("Location: Denver, CO. Abbreviation: DEN.\n"));
	}
	
	/**
	 * Testing shortest distance with valid input.
	 */
	@Test
	void test4() {
		test = new TextUITester("3\nOHD\nSHV");
		
		TextUITester.run();
		
		String output = test.checkOutput().trim();
		
		
		
		assertTrue(output.startsWith("=============================\n") &&
				output.endsWith("(Total Distance: 1582.0 miles.)"));
	
	}
	
	/**
	 * Testing shortest distance with invalid input.
	 */
	@Test
	void test5() {
		test = new TextUITester("3\nOHD\napples\nOTWX\nSHV");
		
		TextUITester.run();
		
		String output = test.checkOutput().trim();
		
		
		
		assertTrue(output.startsWith("=============================\n") &&
				output.endsWith("(Total Distance: 1582.0 miles.)"));
	}
	
	/**
	 * Tests displayDistance doesn't break program with invalid input
	 */
	@Test
	void testIntegration1() {

		try {
		IAirportLoader airportLoader = new AirportLoader();

	    // load the airports from the data file
	    String csv_filename = "airports.dot";
	    
	      List<IAirport> airportList = airportLoader.loadAirports(csv_filename);

	      // instantiate the backend
	      IAirportSearcherBackend backend = new AirportSearcherBackend();

	      // add all the airports to the backend

	      for (IAirport airport : airportList) {
	        backend.addAirport(airport);
	      }

	      // input for the test
	      Scanner userInputScanner = new Scanner("\nATL\n");

	      // instantiate the front end and pass references to the scanner, backend, and IATA validator to it
	      IAirportSearcherFrontend frontend =
	          new AirportSearcherFrontend(userInputScanner, backend);

	      // should throw exception
	      frontend.displayDistance();
	      
	      fail("Exception should be thrown");
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * Tests that displayMST() doesn't break program with invalid input
	 */
	@Test
	void testIntegration2() {
		try {
			IAirportLoader airportLoader = new AirportLoader();

		    // load the airports from the data file
		    String csv_filename = "airports.dot";
		    
		      List<IAirport> airportList = airportLoader.loadAirports(csv_filename);

		      // instantiate the backend
		      IAirportSearcherBackend backend = new AirportSearcherBackend();

		      // add all the airports to the backend

		      for (IAirport airport : airportList) {
		        backend.addAirport(airport);
		      }

		      // input for the test
		      Scanner userInputScanner = new Scanner("ATW\n");

		      // instantiate the front end and pass references to the scanner, backend, and IATA validator to it
		      IAirportSearcherFrontend frontend =
		          new AirportSearcherFrontend(userInputScanner, backend);

		      frontend.displayMST();
		      fail("Exception should be thrown");
		      
			} catch (Exception e) {
				
			}
		}
	
	// Code Review for BackendDeveloper:	
	// 1) AirportSearcherBackend.java is very well commented and the cases are easy to follow.
	// 
	// 2) Methods create a clear-cut process that was very easy to follow
	//	
	// 3) Both files are styled perfectly and enhanced understanding.
	//	
	// 4) Code is as simplified as it can be with the addition of Strings, no further need
	// 	  for more private helper methods.
	//	
	// 5) No bugs could be found by my reading/testing purposes.
		
	/**
	 * Testing every airport in the DOT file was added correctly.
	 */
	@Test
	void codeReviewOfBackendDeveloper1() {
		
		try {
			IAirportLoader airportLoader = new AirportLoader();

		    // load the airports from the data file
		    String csv_filename = "airports.dot";
		    
		      List<IAirport> airportList = airportLoader.loadAirports(csv_filename);

		      // instantiate the backend
		      IAirportSearcherBackend backend = new AirportSearcherBackend();

		      // add all the airports to the backend

		      for (IAirport airport : airportList) {
		        backend.addAirport(airport);
		      }
		      
		      assertEquals(backend.getNumberOfAirports(), 17);
		} catch(Exception e) {
			fail("No exception should be thrown.");
		}
		
	    

	}
	
	/**
	 * Testing that the getShortestDistance method works with two valid airports
	 * (String implementation added after original proposal).
	 */
	@Test
	void codeReviewOfBackendDeveloper2() {
		
		try {
			IAirportLoader airportLoader = new AirportLoader();

		    // load the airports from the data file
		    String csv_filename = "airports.dot";
		    
		      List<IAirport> airportList = airportLoader.loadAirports(csv_filename);

		      // instantiate the backend
		      IAirportSearcherBackend backend = new AirportSearcherBackend();

		      // add all the airports to the backend

		      for (IAirport airport : airportList) {
		        backend.addAirport(airport);
		      }
		      
		      assertEquals(backend.getShortestDistance("ORD", "ATL"), "ORD - - > ATL");
		} catch(Exception e) {
			
		}
		
	}
}
