package we.robots.parkme.messaging.data;

public class GSMData
{

  private ClientData clientData;

  private String to;

  private ClientNotification notification;

  public ClientNotification getNotification()
  {
    return notification;
  }

  public void setNotification(ClientNotification notification)
  {
    this.notification = notification;
  }

  public String getTo()
  {
    return to;
  }

  public void setTo(String to)
  {
    this.to = to;
  }

  public ClientData getClientData()
  {
    return clientData;
  }

  public void setClientData(ClientData clientData)
  {
    this.clientData = clientData;
  }

}
