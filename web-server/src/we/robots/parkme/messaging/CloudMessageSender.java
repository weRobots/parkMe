package we.robots.parkme.messaging;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import sun.misc.IOUtils;

import we.robots.parkme.messaging.data.ClientData;
import we.robots.parkme.messaging.data.ClientNotification;
import we.robots.parkme.messaging.data.GSMData;
import we.robots.parkme.park.OperationStatus.OPERATION_STATUS;
import we.robots.parkme.park.Slot;
import we.robots.parkme.user.User;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CloudMessageSender
{
  private Gson gson;

  private static CloudMessageSender instance = new CloudMessageSender();

  public static CloudMessageSender getInstance()
  {
    return instance;
  }

  private CloudMessageSender()
  {
    gson = new GsonBuilder().create();
  }

  public OPERATION_STATUS sendGCM(String notification, Set<Slot> slotSet)
  {
    for (Slot slot : slotSet)
    {
      try
      {
        sendGCM(slot.getUser(), notification);
      }
      catch (Exception e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
        return OPERATION_STATUS.FAIL;
      }
    }
    return OPERATION_STATUS.SUCCESS;

  }

  private void sendGCM(User user, String notification) throws IOException, JSONException
  {
    GSMData gsmData = new GSMData();
    gsmData.setTo(user.getRegistrationToken());

    ClientNotification clientNotification = new ClientNotification();
    clientNotification.setBody(notification);
    clientNotification.setIcon("parkme");
    clientNotification.setTitle("ParkMe: Notification");

    gsmData.setNotification(clientNotification);
    gsmData.setClientData(new ClientData());

    sendPost(gsmData);

  }

  private void sendPost(GSMData gsmData) throws IOException, JSONException
  {
    String urlStr = "https://gcm-http.googleapis.com/gcm/send";
    //    String urlStr = "https://android.googleapis.com/gcm/send";
//    JSONObject jGcmData = new JSONObject();
//    JSONObject jData = new JSONObject();
//    jData.put("message", "seses3");
////        jGcmData.put("to", "");
//
//    // What to send in GCM message.
////    jGcmData.put("to", gsmData.getTo());
//    jGcmData.put("data", jData);
    //    jGcmData.put(key, value)
    // Create connection to send GCM Message request.
    URL url = new URL(urlStr);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestProperty("Authorization", "key=" + "AIzaSyB-tUxv9kfbxNU3sNJCo6gqIBl35RYxXHQ");
    conn.setRequestProperty("Content-Type", "application/json");
    conn.setRequestMethod("POST");
    conn.setDoOutput(true);

    // Send GCM message content.
    OutputStream outputStream = conn.getOutputStream();
    outputStream.write(gson.toJson(gsmData).getBytes());

    // Read GCM response.
//    InputStream inputStream = conn.getInputStream();
//        String resp = IOUtils.readFully(inputStream);
//        System.out.println(resp);
    System.out.println("Check your device/emulator for notification or logcat for "
        + "confirmation of the receipt of the GCM message.");
  }
}
