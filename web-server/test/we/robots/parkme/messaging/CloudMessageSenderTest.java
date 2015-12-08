package we.robots.parkme.messaging;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import we.robots.parkme.messaging.gsm.CloudMessageSender;
import we.robots.parkme.messaging.mms.MMSMessageHandler;
import we.robots.parkme.park.Slot;
import we.robots.parkme.user.User;

public class CloudMessageSenderTest {

	public void test_GCM() {
		User user = new User();
		user.setRegistrationToken("dZPTe36W-9I:APA91bGt7mNiggn1BIZPfsB3kFszEHAGw2uRVqxhG_tmiOxTgtHCNSDRCGB62lDvNhApAiG5BAEhOi09CajmGBGmX2XbiX6PoO6xY9nOB56zssk0QrG-68c0Se1G6ziEQaNtg31_GXGY");

		user.setUserId("userId");
		Slot slot = new Slot();
		slot.setUser(user);
		Set<Slot> slotSet = new HashSet<Slot>();
		slotSet.add(slot);

		CloudMessageSender.getInstance().sendGCM("ParkMe Message 4", slotSet);
	}

	@Test
	public void test_MMS() {

		List<String> mobiles = new ArrayList<String>();
		mobiles.add("94772929489");

		MMSMessageHandler.sendNotificationsAll(mobiles,
				MMSMessageHandler.NOTIFICATION_TYPE_REMOVE_YOUR_CAR);

/*		MMSMessageHandler.sendNotificationsAll(mobiles,
				MMSMessageHandler.NOTIFICATION_TYPE_NO_PARKING_SLOTS_AVAILABLE);*/

	}

}
