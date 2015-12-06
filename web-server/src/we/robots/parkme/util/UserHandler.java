package we.robots.parkme.util;

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

			return CommonUtil.read(Config.USER_FOLDER, Config.USER_DATA_PREFIX, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	public User readUserDetailsAsObj(String userId) {
		try {

			String xml = CommonUtil.read(Config.USER_FOLDER, Config.USER_DATA_PREFIX, userId);
			return CommonUtil.readObjectFromXMLForUser(xml);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
