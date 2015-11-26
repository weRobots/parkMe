package we.robots.parkme.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import we.robots.parkme.park.CarPark;

/**
 * read saved car park files.
 *
 * @author supun.hettigoda
 */
public final class CarParkFileHandler {
	/**
	 * @param id
	 * @return
	 */
	public static String read(final String id) {
		final String file_path = Config.CAR_PARK_FILE_FOLDER + Config.CAR_PARK_FILE_PREFIX + id + ".xml";

		BufferedReader br;
		final StringBuilder sb = new StringBuilder();

		try {
			br = new BufferedReader(new FileReader(new File(file_path)));

			String line;

			while ((line = br.readLine()) != null) {
				sb.append(line.trim());
			}

			br.close();

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	/**
	 * @param carPark
	 * @param id
	 * @return
	 */
	public static boolean save(CarPark carPark, String id) {
		final String file_path = Config.CAR_PARK_FILE_FOLDER + Config.CAR_PARK_FILE_PREFIX + id + ".xml";

		BufferedWriter out = null;

		try {
			out = new BufferedWriter(new FileWriter(file_path));
			out.write(toXML(carPark));

		} catch (IOException e1) {

			e1.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}

		return false;
	}

	private static String toXML(CarPark carPark) {
		XStream xstream = new XStream(new StaxDriver());
		return xstream.toXML(carPark);
	}
}