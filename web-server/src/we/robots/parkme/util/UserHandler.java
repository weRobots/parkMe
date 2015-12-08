package we.robots.parkme.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import we.robots.parkme.user.User;

public class UserHandler {

	private static UserHandler instance = new UserHandler();

	private UserHandler() {

	}

	public static UserHandler getInstance() {
		return instance;
	}

	public String saveUserDetails(User user) {
		String id = user.getUserId();
		try {

			if (id == null || id.isEmpty() || id.endsWith("null")) {
				UUID uuid = UUID.randomUUID();
				id = uuid.toString();
			}

			user.setUserId(id);
			CarParkFileHandler.save(user);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return CommonUtil.toXML(user);
	}

	public String readUserDetailsAsXML(String userId) {
		try {

			return CommonUtil.read(Config.USER_FOLDER, Config.USER_DATA_PREFIX,
					userId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public User readUserDetailsAsObj(String userId) {
		try {

			String xml = CommonUtil.read(Config.USER_FOLDER,
					Config.USER_DATA_PREFIX, userId);
			return CommonUtil.readObjectFromXMLForUser(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static User findUserByMobile(String mobile) {
		String target_dir = Config.USER_FOLDER;
		File dir = new File(target_dir);
		File[] files = dir.listFiles();
		User matchUser = null;

		for (File f : files) {
			if (f.isFile()) {
				BufferedReader inputStream = null;
				final StringBuilder sb = new StringBuilder();

				try {
					inputStream = new BufferedReader(new FileReader(f));
					String line;

					while ((line = inputStream.readLine()) != null) {
						sb.append(line.trim());
					}

					inputStream.close();

					User user2 = CommonUtil.readObjectFromXMLForUser(sb
							.toString());

					// match for mobile
					if (user2.getMobileNumber().equals(mobile)) {
						matchUser = user2;
						break;
					}
				} catch (IOException e) {

				}

			}
		}

		return matchUser;
	}
}
