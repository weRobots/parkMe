package we.robots.parkme.messaging;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import we.robots.parkme.messaging.data.ClientData;
import we.robots.parkme.messaging.data.ClientNotification;
import we.robots.parkme.messaging.data.GSMData;
import we.robots.parkme.park.OperationStatus.OPERATION_STATUS;
import we.robots.parkme.park.Slot;
import we.robots.parkme.user.User;

public class CloudMessageSender {

	private static CloudMessageSender instance = new CloudMessageSender();

	public static CloudMessageSender getInstance() {
		return instance;
	}

	public OPERATION_STATUS sendGCM(String notification, Set<Slot> slotSet) {
		for (Slot slot : slotSet) {
			try {
				sendGCM(slot.getUser(), notification);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return OPERATION_STATUS.FAIL;
			}
		}
		return OPERATION_STATUS.SUCCESS;

	}

	private void sendGCM(User user, String notification) throws IOException, JSONException {
		GSMData gsmData = new GSMData();
		gsmData.setTo(user.getRegistrationToken());

		ClientNotification clientNotification = new ClientNotification();
		clientNotification.setBody(notification);
		clientNotification.setIcon("parkme");
		clientNotification.setTitle("ParkMe: Notification");

		gsmData.setNotification(clientNotification);
		ClientData clientData = new ClientData();
		clientData.setUserId(user.getUserId());

		gsmData.setData(clientData);

		sendPost(gsmData);

	}

	private void sendPost(GSMData gsmData) throws IOException, JSONException {
		try {

			// Prepare JSON containing the GCM message content. What to send and
			// where to send.
			JSONObject jGcmData = new JSONObject();
			JSONObject jData = new JSONObject();
			jData.put("message", gsmData.getNotification().getBody());
			// Where to send GCM message.

			jGcmData.put("to", gsmData.getTo());

			// What to send in GCM message.
			jGcmData.put("data", jData);

			// Create connection to send GCM Message request.
			URL url = new URL("https://gcm-http.googleapis.com/gcm/send");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Authorization", "key=" + "AIzaSyB-tUxv9kfbxNU3sNJCo6gqIBl35RYxXHQ");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// Send GCM message content.
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(jGcmData.toString().getBytes());

			System.out.println("Check your device/emulator for notification or logcat for "
					+ "confirmation of the receipt of the GCM message.");
		} catch (IOException e) {
			System.out.println("Unable to send GCM message.");
			System.out.println("Please ensure that API_KEY has been replaced by the server "
					+ "API key, and that the device's registration token is correct (if specified).");
			e.printStackTrace();
		}

	}

}
