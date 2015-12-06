package we.robots.parkme.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import we.robots.parkme.park.CarPark;
import we.robots.parkme.user.User;

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
	public static String readCarPark(final String id) {
		final String file_path = Config.CAR_PARK_FILE_FOLDER + Config.CAR_PARK_FILE_PREFIX + id + ".xml";
		return read(file_path);
	}

	/**
	 * @return
	 */
	public static String readAll() {
		final String file_path = Config.CAR_PARK_FILE_FOLDER + Config.CONFIGURED_CAR_PARKS_FILE_PREFIX + ".xml";
		return read(file_path);
	}

	private static String read(final String path) {
		BufferedReader br;
		final StringBuilder sb = new StringBuilder();

		try {
			br = new BufferedReader(new FileReader(new File(path)));

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
	public static boolean save(CarPark carPark) {
		final String file_path = Config.CAR_PARK_FILE_FOLDER + Config.CAR_PARK_FILE_PREFIX + carPark.getId() + ".xml";

		BufferedWriter out = null;

		try {
			out = new BufferedWriter(new FileWriter(file_path));
			out.write(CommonUtil.toXML(carPark));

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

	/**
	 * @param carPark
	 * @param id
	 * @return
	 */
	public static boolean save(User user) {
		final String file_path = Config.USER_FOLDER + Config.USER_DATA_PREFIX + user.getUserId() + ".xml";

		BufferedWriter out = null;

		try {
			out = new BufferedWriter(new FileWriter(file_path));
			out.write(CommonUtil.toXML(user));

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

	/**
	 * @param carPark
	 * @param id
	 * @return
	 */
	public static boolean saveAllConfigured(List<CarPark> list) {
		final String file_path = Config.CAR_PARK_FILE_FOLDER + Config.CONFIGURED_CAR_PARKS_FILE_PREFIX + ".xml";

		BufferedWriter out = null;

		try {
			out = new BufferedWriter(new FileWriter(file_path));
			out.write(CommonUtil.toXML(list));

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

}
