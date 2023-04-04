// --== CS400 File Header Information ==--
// Name: Faris Hazim Mohamed Zaimir
// Email: mohamedzaimi@wisc.edu
// Team: AA
// TA: Yuye Jiang
// Lecturer: Gary Dahl
// Notes to Grader: -

// The File class is defined in the java.io package
import java.io.File;

// The FileNotFoundException class is defined in the java.io package
import java.io.FileNotFoundException;

// The ArrayList class is defined in the java.util package
import java.util.ArrayList;

// The List interface is defined in the java.util package
import java.util.List;

// The Scanner class is defined in the java.util package
import java.util.Scanner;

// The Hashtable class is defined in the java.util package
import java.util.Hashtable;

/**
 * This class implements the IAirportLoader interface to create a list of Airports and their
 * associated data from the passed DOT file(airports.dot).
 * 
 * @author Faris Hazim Mohamed Zaimir
 */
public class AirportLoader implements IAirportLoader {

  protected List<IAirport> airportList;

  /**
   * This method loads the list of Airports from a DOT file.
   * 
   * @param filepathToDOT path to the DOT file relative to the executable
   * @return a list of Airport objects
   * @throws FileNotFoundException when an attempt to open file denoted by a specified pathname has
   *                               failed
   */
  @Override
  public List<IAirport> loadAirports(String filepathToDOT) throws FileNotFoundException {

    // Checks if the file path exists; a FileNotFoundException will be thrown if file does not
    // exist
    File filePath = new File(filepathToDOT);
    if (!filePath.exists()) {
      throw new FileNotFoundException("File does not exist");
    }

    // Hashtable to store the 3-letter IATA code as key and the respective Airport name as value
    // Useful for constructing Airport objects later
    Hashtable<String, String> hashTemp = new Hashtable<>();

    // Hashtable to store the 3-letter IATA code as key and the respective Airport object as value
    // Values will be passed to the ArrayList of Airports later
    Hashtable<String, IAirport> hashAirport = new Hashtable<>();

    // Stores respective Airport attributes when lines are split according to the passed
    // regular expression argument
    String[] dataIATA;
    String[] dataName;
    String[] dataDistances;

    String temp;

    // Scans the DOT file
    Scanner in = new Scanner(filePath);

    // Skips the first line of the DOT file
    in.nextLine();

    // Step 1 - Store all Airport IATA codes as key and Airport name as value in a hashtable
    while (in.hasNextLine()) {
      temp = in.nextLine();

      // Breaks out of the loop when there is an empty line (indicates that there are no more
      // Airport IATA codes or names to be stored)
      if (temp.trim().equals("")) {
        break;
      } else {
        dataIATA = temp.split("\\[");
        dataName = temp.split("\"");
        hashTemp.put(dataIATA[0].trim(), dataName[1]);
      }
    }

    // Step 2 - Store all Airport IATA codes as key and Airport objects as value in a hashtable
    while (in.hasNextLine()) {
      temp = in.nextLine();

      // Breaks out of the loop when there is an empty line (indicates that there all data has been
      // stored)
      if (temp.trim().equals("")) {
        break;
      } else {
        dataIATA = temp.split("[(--)\\[]");
        dataDistances = temp.split("\"");
        String sourceAirport = dataIATA[0].trim();
        String destinationAirport = dataIATA[2].trim();
        Integer weight = Integer.parseInt(dataDistances[1]);

        // Step 2A - Stores source Airport data
        if (!hashAirport.containsKey(sourceAirport)) {

          // List for the 3-letter IATA codes of the destination Airport
          List<String> destIATA = new ArrayList<>();

          // List for the names of the destination Airport
          List<String> destName = new ArrayList<>();

          // List for the distances between the source Airport and destination Airport
          List<Integer> distances = new ArrayList<>();

          destIATA.add(destinationAirport);
          destName.add(hashTemp.get(destinationAirport));
          distances.add(weight);

          hashAirport.put(sourceAirport, new Airport(hashTemp.get(sourceAirport), sourceAirport,
              distances, destName, destIATA));

          // When hashtable already has data of the source Airport, these lines of code will be
          // executed to store additional data
        } else if (hashAirport.containsKey(sourceAirport)) {
          hashAirport.get(sourceAirport).distances().add(weight);
          hashAirport.get(sourceAirport).destinationsIATA().add(destinationAirport);
          hashAirport.get(sourceAirport).destinationsList().add(hashTemp.get(destinationAirport));
        }

        // Step 2B - Since graph is undirected, we need to store data from the destination Airport
        // to source Airport too
        if (!hashAirport.containsKey(destinationAirport)) {

          // List for the 3-letter IATA codes of the source Airport
          List<String> sourceIATA = new ArrayList<>();

          // List for the names of the source Airport
          List<String> sourceName = new ArrayList<>();

          // List for the distances between the destination Airport and source Airport
          List<Integer> distances = new ArrayList<>();

          sourceIATA.add(sourceAirport);
          sourceName.add(hashTemp.get(sourceAirport));
          distances.add(weight);

          hashAirport.put(destinationAirport, new Airport(hashTemp.get(destinationAirport),
              destinationAirport, distances, sourceName, sourceIATA));

          // When hashtable already has data of the destination Airport, these lines of code will be
          // executed to store additional data
        } else if (hashAirport.containsKey(destinationAirport)) {
          hashAirport.get(destinationAirport).distances().add(weight);
          hashAirport.get(destinationAirport).destinationsIATA().add(sourceAirport);
          hashAirport.get(destinationAirport).destinationsList().add(hashTemp.get(sourceAirport));
        }
      }
    }

    in.close();

    // Creates a new ArrayList that holds all 17 Airport objects along with their associated data
    airportList = new ArrayList<>(hashAirport.values());

    return airportList;
  }
}
