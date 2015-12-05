package we.robots.parkme.messaging.mms;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MMSMessageHandler {

	public static final int NOTIFICATION_TYPE_REMOVE_YOUR_CAR = 1;

	private static final String USER_NAME = "cadd.team8@gmail.com";

	private static final String PWD = "cadd123456789";

	public static void sendNotificationsAll(List<String> mobiles, int notificationType) {
		if (NOTIFICATION_TYPE_REMOVE_YOUR_CAR == notificationType) {
			for (String mobile : mobiles) {
				MMS mms = getMMSForCarRemoval(mobile);
				if (mms != null) {
					send(mms);
				}
			}
		}
	}

	private static MMS getMMSForCarRemoval(String phone) {
		String number = phone;
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
			if (MMS.SERVICE_PROVIDER_MOBITEL.equals(formattedNumber.substring(2, 4))) {
				mms = new MobitelMMS(formattedNumber, MMS.MESSAGE_TYPE_REMOVE_YOUR_CAR);
			} else if (MMS.SERVICE_PROVIDER_DIALOG.equals(formattedNumber.substring(2, 4))) {
				mms = new DialogMMS(formattedNumber, MMS.MESSAGE_TYPE_REMOVE_YOUR_CAR);
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

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(MMS.FROM_ADDRESS));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mms.getToAddress()));
			message.setSubject(mms.getSubject());
			message.setText(mms.getText());

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
