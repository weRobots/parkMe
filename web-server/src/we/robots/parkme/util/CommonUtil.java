package we.robots.parkme.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.InputSource;

import we.robots.parkme.common.ParkMeSaveData;
import we.robots.parkme.park.CarPark;
import we.robots.parkme.park.Slot;
import we.robots.parkme.user.User;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

/**
 * @author supun.hettigoda
 */
public final class CommonUtil {
	/**
	 * @param value
	 *
	 * @return
	 */
	public static boolean checkNotNullAndNotEmpty(final Collection<?> value) {
		return ((value != null) && !value.isEmpty());
	}

	/**
	 * @param value
	 *
	 * @return
	 */
	public static boolean checkNotNull(final Object value) {
		return (value != null);
	}

	/**
	 * @param set
	 * @param element
	 */
	public static void addSafely(final Set<Slot> set, final Slot element) {
		if (checkNotNull(set)) {
			if (checkNotNull(element)) {
				set.add(element);
			}
		}
	}

	/**
	 * @param set
	 * @param element
	 */
	public static void addAllSafely(final Set<Slot> set,
			final Set<Slot> elements) {
		if (checkNotNull(set)) {
			if (checkNotNullAndNotEmpty(elements)) {
				set.addAll(elements);
			}
		}
	}

	/**
	 * @param slots
	 * @return
	 */
	public static HashMap<String, Slot> convert(Set<Slot> slots) {
		HashMap<String, Slot> map = new HashMap<String, Slot>();

		for (Slot slot : slots) {
			map.put(slot.getId(), slot);
		}

		return map;
	}

	public static String toXML(CarPark data) {
		XStream xstream = new XStream(new StaxDriver());
		xstream.autodetectAnnotations(true);
		return formatXml(xstream.toXML(data));
	}

	public static String toXML(List<CarPark> data) {
		XStream xstream = new XStream(new StaxDriver());
		xstream.autodetectAnnotations(true);
		return formatXml(xstream.toXML(data));
	}

	public static String toXML(User data) {
		XStream xstream = new XStream(new StaxDriver());
		xstream.autodetectAnnotations(true);
		return formatXml(xstream.toXML(data));
	}

	public static String formatXml(String xml) {

		try {
			Transformer serializer = SAXTransformerFactory.newInstance()
					.newTransformer();

			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "2");

			Source xmlSource = new SAXSource(new InputSource(
					new ByteArrayInputStream(xml.getBytes())));
			StreamResult res = new StreamResult(new ByteArrayOutputStream());

			serializer.transform(xmlSource, res);

			return new String(
					((ByteArrayOutputStream) res.getOutputStream())
							.toByteArray());

		} catch (Exception e) {
			return xml;
		}
	}

	public static String read(String folder, String prefix, final String id) {
		final String file_path = folder + prefix + id + ".xml";

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

	public static User readObjectFromXMLForUser(String xml) {

		XStream xstream = new XStream(new StaxDriver());
		xstream.processAnnotations(User.class); // inform XStream to parse
												// annotations in Data class

		User user = (User) xstream.fromXML(xml); // parse
		return user;

	}

	public static CarPark readObjectFromXMLForCarPark(String xml) {

		XStream xstream = new XStream(new StaxDriver());
		xstream.processAnnotations(CarPark.class); // inform XStream to parse
													// annotations in Data class

		CarPark user = (CarPark) xstream.fromXML(xml); // parse
		return user;

	}

	public static List<CarPark> readCarParkList(String xml) {

		XStream xstream = new XStream(new StaxDriver());
		xstream.processAnnotations(CarPark.class); // inform XStream to parse
													// annotations in Data class

		@SuppressWarnings("unchecked")
		List<CarPark> list = (List<CarPark>) xstream.fromXML(xml); // parse
		return list;

	}
}
