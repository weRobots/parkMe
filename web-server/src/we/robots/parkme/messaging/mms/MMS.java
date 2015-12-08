package we.robots.parkme.messaging.mms;

public interface MMS
{
  String FROM_ADDRESS = "ADMINISTRATOR.CADD.TEAM8";

  String MMS_DOMAIN_DIALOG = "@mms.dialog.lk";

  String MMS_DOMAIN_MOBITEL = "@mms.mobitel.lk";
  
  String SERVICE_PROVIDER_MOBITEL = "71";
  
  String SERVICE_PROVIDER_DIALOG = "77";
  
  String TELE_CODE_SRL = "94";

  int MESSAGE_TYPE_REMOVE_YOUR_CAR = 1;

  int MESSAGE_TYPE_NO_PARKING_SLOTS_AVAILABLE = 2;

  String getToAddress();

  String getSubject();

  String getText();

}
