package we.robots.parkme;

import java.util.List;

import org.junit.Test;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import we.robots.parkme.park.CarPark;
import we.robots.parkme.util.CarParkFileHandler;
import we.robots.parkme.util.CommonUtil;
import we.robots.parkme.util.DistanceCalculator;

public class DistanceCalculatorTest {
	
	@Test
	public void test_calculation()
	{
		Double distance =  DistanceCalculator.distance(6.9068216, 79.852994, 6.9081691, 79.8537161);
		System.out.println(distance);
		distance =  DistanceCalculator.distance(6.9068305, 79.8529181, 6.9081691, 79.8537161);		
		System.out.println(distance);
		distance =  DistanceCalculator.distance(6.9068155, 79.8528996, 6.9068305, 79.8529181);		
		System.out.println(distance);
	}
	
	@Test
	public void test_read_all_carParks()
	{
		List<CarPark> carParks =  CommonUtil.readCarParkList(CarParkFileHandler
				.readAll());
		
		System.out.println(new XStream(new StaxDriver()).toXML(carParks));
	}

}
