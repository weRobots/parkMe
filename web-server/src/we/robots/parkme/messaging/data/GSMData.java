package we.robots.parkme.messaging.data;

public class GSMData
{

  private ClientData clientData;

  private String to;

  private ClientNotification notification;
  
  private String topic;
  
  

  public String getTopic()
  {
    return topic;
  }

  public void setTopic(String topic)
  {
    this.topic = topic;
  }

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
