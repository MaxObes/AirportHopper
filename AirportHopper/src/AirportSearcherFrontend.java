// --== CS400 Project Three File Header ==--
// Name: Maxwell Oberbrunner
// CSL Username: oberbrunner
// Email: moberbrunner@wisc.edu
// Lecture #: <001 @11:00am>
// Notes to Grader: -

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AirportSearcherFrontend implements IAirportSearcherFrontend {

	// Store user input
	private Scanner user_input;

	// Instantiate an object of the IFIFARankerBackend class
	private IAirportSearcherBackend backend;
	
	/**
	 * Create an AirportSearcherFronted
	 * 
	 * @param userInputScanner one Scanner for System.in
	 * @param backend access to BD implementation
	 */
	public AirportSearcherFrontend(Scanner userInputScanner, IAirportSearcherBackend backend) {
		this.user_input = userInputScanner;
		this.backend = backend;
	}
	
	/**
	 * This method exists for the sole purpose of letting the frontend developer
	 * avoiding writing System.out.println so many times. Now he/she can just write
	 * print()
	 * 
	 * @param strToPrint the string to print, using System.out.println()
	 */
	private static void print(String strToPrint) {
		System.out.println(strToPrint);
	}
	
	/**
	 * Creates Text-Based UI for user to interact with FlightBooker
	 */
	@Override
	public void runCommandLoop() {
		print("=============================");
		print("Welcome to Flight Booker 1.0!");
		print("=============================");

		// While loop to keep the program running after user input an answer
				while (true) {
					this.displayMainMenu();
					int menuSelection;
					while (true) {
						String input = user_input.nextLine();
						try {
							// Throw exception if input is not an int
							menuSelection = Integer.parseInt(input);
							if (menuSelection >= 1 && menuSelection <= 4) {
								break; // valid input
							}
							print("Number entered must be 1-4");
						} catch (NumberFormatException e1) {
							print("Please enter an integer");
						}
					}

					// at this point, menuSelection is necessarily between 1 and 4
					if (menuSelection == 1) {
						this.displayMST();
					} else if (menuSelection == 2) {
						this.displayDistance();
					} else if (menuSelection == 3) {
						this.displayAirports(backend.getAllAirports());
					} else {
						print("Safe Travels!");
						break;
					}
				}
	}

	/**
	 * Displays the main menu String for runCommandLoop()
	 */
	@Override
	public void displayMainMenu() {
		// TODO Auto-generated method stub
		print("You are in the Main Menu: ");
		print("          1) Display MST");
		print("          2) Get the Distance between Two Airports");
		print("          3) Display a Full List of Airports");
		print("          4) Exit Application");
	}

	/**
	 * Displays a list of all the airports for runCommandLoop()
	 */
	@Override
	public void displayAirports(List<String> airports) {
		String currString = "";
		
		for (int i = 0; i < airports.size(); i++) {
			print("" + (i + 1) + ") " + currString + airports.get(i));
		}
		
		print("");
		
	}
	
	/**
	 * Displays the MST for runCommandLoop()
	 */
	@Override
	public void displayMST() {
		System.out.print("Which airport would you like to start from? ");
		String startingAirport = user_input.next();
		print("Displaying MST starting from " + startingAirport + ":");
		try {
			print(backend.getMST(startingAirport));
		} catch (NoSuchElementException e) {
			print(e.getMessage());
			print("");
		}
	}

	/**
	 * Displays the distance between two airports using BD algorithm
	 */
	@Override
	public void displayDistance() {
		String firstAirport = "";
		String secondAirport = "";
		
		print("You've selected the distance menu.");
		
		// get the first airport
		while (true) {
			
			System.out.print("   Please enter a starting airport: ");
			firstAirport = user_input.next();
			
			if (firstAirport.length() != 3) { // need to check if it exists
				print("     Abbreviation must be length 3. Please try again");
				continue;
			}
			
			break;
		}
		// get the second airport
		while (true) {
			System.out.print("   Please enter an ending airport: ");
			secondAirport = user_input.next();
			
			if (secondAirport.length() != 3) { // need to check if it exists
				System.out.println("     Abbreviation must be length 3. Please try again");
				continue;
			}
			
			break;
		}
		
		try {
			print("The shortest flight between these two airports is:");
			print(backend.getShortestDistance(firstAirport, secondAirport));
			print("(Total Distance: " + backend.getTotalPathDistance(firstAirport, secondAirport) + " miles.)");
			print("");
		} catch (NoSuchElementException e) {
			print(e.getMessage());
			print(""); // create spacing line
		} 
		
		
	}

}
