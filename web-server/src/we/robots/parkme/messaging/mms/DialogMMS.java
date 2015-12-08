package we.robots.parkme.messaging.mms;

public class DialogMMS implements MMS
{
  private String mobile = "";

  private String subject = "";

  private String text = "";

  public DialogMMS(String mobileNo, int messageType)
  {
    this.mobile = mobileNo;
    if (messageType == MESSAGE_TYPE_REMOVE_YOUR_CAR)
    {
      subject = "REMOVE your car";
      text = "Admin message, Please respond immediately";
    }
    else if (messageType == MESSAGE_TYPE_NO_PARKING_SLOTS_AVAILABLE)
    {
      subject = "NO PARKING slot available for your car";
      text = "Admin message, Please find alternative";
    }
  }

  public DialogMMS(String mobileNo, String subject, String text)
  {
    this.mobile = mobileNo;
    this.subject = subject;
    this.text = text;
  }

  public String getToAddress()
  {
    return mobile + MMS_DOMAIN_DIALOG;
  }

  public String getSubject()
  {
    return subject;
  }

  public String getText()
  {
    return text;
  }

}
