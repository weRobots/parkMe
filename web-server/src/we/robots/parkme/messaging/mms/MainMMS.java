package we.robots.parkme.messaging.mms;

import java.util.ArrayList;
import java.util.List;

public class MainMMS {

	public static void main(String[] args) {
		List<String> mobiles = new ArrayList<String>();
		// mobiles.add("9471XXXXXXX");
		// mobiles.add("071XXXXXXX");
		// mobiles.add("71XXXXXXX");
		// mobiles.add("077XXXXXXX");
		// mobiles.add("77XXXXXXX");
		// mobiles.add("9477XXXXXXX");

		MMSMessageHandler.sendNotificationsAll(mobiles, MMSMessageHandler.NOTIFICATION_TYPE_REMOVE_YOUR_CAR);
	}
}
