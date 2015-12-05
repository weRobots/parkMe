package we.robots.parkme.util;

/**
 * to calculate the distance between two location points
 * 
 * @author suppa
 *
 */
public class DistanceCalculator {

	/**
	 * gives the distance between p1 and p2 location points by Meters.
	 * 
	 * @param p1_lat
	 * @param p1_lon
	 * @param p2_lat
	 * @param p2_lon
	 * @param unit
	 * @return
	 */
	public static double distance(double p1_lat, double p1_lon, double p2_lat, double p2_lon) {
		double theta = p1_lon - p2_lon;
		double dist = Math.sin(deg2rad(p1_lat)) * Math.sin(deg2rad(p2_lat))
				+ Math.cos(deg2rad(p1_lat)) * Math.cos(deg2rad(p2_lat)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;

		// convert to meters
		dist = dist * 1609.344;

		return (dist);
	}

	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts decimal degrees to radians : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	/* :: This function converts radians to decimal degrees : */
	/* ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: */
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

}
