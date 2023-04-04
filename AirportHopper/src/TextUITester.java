// --== CS400 Project Three File Header ==--
// Name: Maxwell Oberbrunner
// CSL Username: oberbrunner
// Email: moberbrunner@wisc.edu
// Lecture #: <001 @11:00am>
// Notes to Grader: -

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * This class can be used to test text based user interactions by 1) specifying
 * a String of text input (that will be fed to System.in as if entered by the user),
 * and then 2) capturing the output printed to System.out and System.err in String
 * form so that it can be compared to the expect output.
 * @author dahl
 * @date 2021.10
 */
public class TextUITester {

    /**
     * This main method demonstrates the use of a TextUITester object to check
     * the behavior of the following run method.
     * @param args from the commandline are not used in this example
     */
    public static void main(String[] args) {

    	/*	
        // 1. Create a new TextUITester object for each test, and
        // pass the text that you'd like to simulate a user typing as only argument.
        TextUITester tester = new TextUITester("apple\n3.14\nq\n");

        // 2. Run the code that you want to test here:
        run(); // (this code should read from System.in and write to System.out)

        // 3. Check whether the output printed to System.out matches your expectations.
        String output = tester.checkOutput();
        if(output.startsWith("Welcome to the run program.") && 
           output.contains("apple4.14"))
            System.out.println("Test passed.");
        else
            System.out.println("Test FAILED.");
            */
        
        
    }   
    
    /**
     * This is the code being tested by the main method above.
     * It 1) prints out a welcome message, 
     *    2) reads a String, a double, and a character from System.in, and then
     *    3) prints out the string followed by a number that is one greater than that double,
     *       if the character that it read in was a (lower case) 'q'.
     */
    public static void run() {
        // Note to avoid instantiating more than one Scanner reading from System.in in your code!
        Scanner in = new Scanner(System.in); 
        AirportSearcherBackend_FD back = new AirportSearcherBackend_FD();
        AirportSearcherFrontend front = new AirportSearcherFrontend(in, back);
        int menuSelection = 0;
        System.out.println("=============================");
        System.out.println("Welcome to Flight Booker 1.0!");
        System.out.println("=============================");
        System.out.println("You are in the Main Menu: ");
        System.out.println("          1) Display MST");
        System.out.println("          2) Get the Distance between Two Airports");
        System.out.println("          3) Display a Full List of Airports");
        System.out.println("          4) Exit Application");
        while (in.hasNextLine()) {
        	String input = in.nextLine();
        	System.out.println(input);
			try {
				// Throw exception if input is not an int
				menuSelection = Integer.parseInt(input);
				if (menuSelection >= 1 && menuSelection <= 4) {
					break; // valid input
				}
				System.out.println("Number entered must be 1-4");
			} catch (NumberFormatException e1) {
				System.out.println("Please enter an integer");
			}
        }
        // at this point, menuSelection is necessarily between 1 and 4
     			if (menuSelection == 1) {
     				front.displayMST();
     			} else if (menuSelection == 2) {
     				front.displayAirports(back.getAllAirports());
     			} else if (menuSelection == 3) {
     				front.displayDistance();
     			} else {
     				System.out.println("Goodbye!");
     			}
     	        in.close();
    }

    // Below is the code that actually implements the redirection of System.in and System.out,
    // and you are welcome to ignore this code: focusing instead on how the constructor and
    // checkOutput() method is used int he example above.

    private PrintStream saveSystemOut; // store standard io references to restore after test
    private PrintStream saveSystemErr;
    private InputStream saveSystemIn;
    private ByteArrayOutputStream redirectedOut; // where output is written to durring the test
    private ByteArrayOutputStream redirectedErr;

    /**
     * Creates a new test object with the specified string of simulated user input text.
     * @param programInput the String of text that you want to simulate being typed in by the user.
     */
    public TextUITester(String programInput) {
       
    	
    	// backup standard io before redirecting for tests
        saveSystemOut = System.out;
        saveSystemErr = System.err;
        saveSystemIn = System.in;    
        // create alternative location to write output, and to read input from
        System.setOut(new PrintStream(redirectedOut = new ByteArrayOutputStream()));
        System.setErr(new PrintStream(redirectedErr = new ByteArrayOutputStream()));
        System.setIn(new ByteArrayInputStream(programInput.getBytes()));
        
        
    }

    /**
     * Call this method after running your test code, to check whether the expected
     * text was printed out to System.out and System.err.  Calling this method will 
     * also un-redirect standard io, so that the console can be used as normal again.
     * 
     * @return captured text that was printed to System.out and System.err durring test.
     */
    public String checkOutput() {
        try {
            String programOutput = redirectedOut.toString() + redirectedErr.toString();
            return programOutput;    
        } finally {
            // restore standard io to their pre-test states
            System.out.close();
            System.setOut(saveSystemOut);
            System.err.close();
            System.setErr(saveSystemErr);
            System.setIn(saveSystemIn);    
        }
    }
}