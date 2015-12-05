package we.robots.parkme.messaging;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

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

  private void sendGCM(User user, String notification) throws IOException
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

  private void sendPost(GSMData gsmData) throws IOException
  {

    String url = "https://gcm-http.googleapis.com/gcm/send";
    URL obj = new URL(url);
    HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

    //add reuqest header
    con.setRequestMethod("POST");
    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
    con.setRequestProperty("Content-Type", "Content-Type:application/json");

    String urlParameters = gson.toJson(gsmData);

    // Send post request
    con.setDoOutput(true);
    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
    wr.writeBytes(urlParameters);
    wr.flush();
    wr.close();

    int responseCode = con.getResponseCode();
    System.out.println("\nSending 'POST' request to URL : " + url);
    System.out.println("Post parameters : " + urlParameters);
    System.out.println("Response Code : " + responseCode);

    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null)
    {
      response.append(inputLine);
    }
    in.close();

    //print result
    System.out.println(response.toString());

  }
}
