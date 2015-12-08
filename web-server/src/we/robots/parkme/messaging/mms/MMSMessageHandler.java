package we.robots.parkme.messaging.mms;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import we.robots.parkme.park.Slot;

public class MMSMessageHandler {

	public static final int NOTIFICATION_TYPE_REMOVE_YOUR_CAR = 1;

	public static final int NOTIFICATION_TYPE_NO_PARKING_SLOTS_AVAILABLE = 2;

	private static final String USER_NAME = "cadd.team8@gmail.com";

	private static final String PWD = "cadd123456789";

	public static void sendNotifications(Set<Slot> slots, int notificationType) {
		System.out.println("sending MMS started...");
		List<String> mobiles = new ArrayList<String>();
		for (Slot slot : slots) {

			if (slot.getUser().getMobileNumber() != null) {
				mobiles.add(slot.getUser().getMobileNumber());
				System.out.println("sending to slot[" + slot.getId()
						+ "] to mobile[" + slot.getUser().getMobileNumber()
						+ "]");
			} else {
				System.out.println("sending fail, slot[" + slot.getId()
						+ "] no mobile number defined");
			}
		}

		sendNotificationsAll(mobiles, notificationType);

	}

	public static void sendNotificationsAll(List<String> mobiles,
			int notificationType) {
		int type = -1;

		if (NOTIFICATION_TYPE_REMOVE_YOUR_CAR == notificationType) {
			type = MMS.MESSAGE_TYPE_REMOVE_YOUR_CAR;
		} else if (NOTIFICATION_TYPE_NO_PARKING_SLOTS_AVAILABLE == notificationType) {
			type = MMS.MESSAGE_TYPE_NO_PARKING_SLOTS_AVAILABLE;
		}

		if (type > 0) {
			for (String mobile : mobiles) {
				MMS mms = getMMS(mobile, type);
				if (mms != null) {
					send(mms);
				}
			}
		}
	}

	private static MMS getMMS(String mobile, int type) {
		String number = mobile;
		String formattedNumber = "";
		if (number != null) {
			if (number.length() == 11) {
				formattedNumber = number;
			} else if (number.length() == 10) {
				formattedNumber = MMS.TELE_CODE_SRL + number.substring(1);
			} else if (number.length() == 9) {
				formattedNumber = MMS.TELE_CODE_SRL + number;
			}
		}

		System.out.println("do this : Send MMS : " + formattedNumber);

		MMS mms = null;
		if (formattedNumber.length() > 0) {
			if (MMS.SERVICE_PROVIDER_MOBITEL.equals(formattedNumber.substring(
					2, 4))) {
				mms = new MobitelMMS(formattedNumber, type);
			} else if (MMS.SERVICE_PROVIDER_DIALOG.equals(formattedNumber
					.substring(2, 4))) {
				mms = new DialogMMS(formattedNumber, type);
			}

		}
		return mms;
	}

	private static void send(MMS mms) {
		final String username = USER_NAME;
		final String password = PWD;

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mms.FROM_ADDRESS));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mms.getToAddress()));
			message.setSubject(mms.getSubject());
			message.setText(mms.getText());

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
