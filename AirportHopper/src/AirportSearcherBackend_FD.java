// --== CS400 Project Three File Header ==--
// Name: Maxwell Oberbrunner
// CSL Username: oberbrunner
// Email: moberbrunner@wisc.edu
// Lecture #: <001 @11:00am>
// Notes to Grader: -

import java.util.ArrayList;
import java.util.List;

public class AirportSearcherBackend_FD implements IAirportSearcherBackend {

	@Override
	public void addAirport(IAirport airport) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getAllAirports() {
		List<String> airports = new ArrayList<String>();
		
		airports.add("Location: Denver, CO. Abbreviation: DEN.");
		
		return airports;
	}

	@Override
	public int getNumberOfAirports() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getMST(String airport) {
		
		return "ORD -> ATW -> SHV";
	}

	@Override
	public String getShortestDistance(String source, String destination) {
		return "ORD -> SHV";
	}

	@Override
	public double getTotalPathDistance(String source, String destination) {
		// TODO Auto-generated method stub
		return 1582;
	}

}
