// --== CS400 Project Three File Header ==--
// Name: Maxwell Oberbrunner
// CSL Username: oberbrunner
// Email: moberbrunner@wisc.edu
// Lecture #: <001 @11:00am>
// Notes to Grader: -

import java.util.List;

public class Airport_FD implements IAirport {

	String name;
	int IATA;
	
	public Airport_FD(String name) {
		this.IATA = 100;
		this.name = name;
	}
	
	@Override
	public String getIATA() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Integer> distances() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> destinationsIATA() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> destinationsList() {
		// TODO Auto-generated method stub
		return null;
	}

}
