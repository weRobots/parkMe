package we.robots.parkme.messaging;

import java.util.HashSet;
import java.util.Set;

import we.robots.parkme.park.Slot;
import we.robots.parkme.user.User;

public class CloudMessageSenderTest
{

  public static void main(String[] args)
  {
    User user = new User();
//    user.setRegistrationToken("dZPTe36W-9I:APA91bGt7mNiggn1BIZPfsB3kFszEHAGw2uRVqxhG_tmiOxTgtHCNSDRCGB62lDvNhApAiG5BAEhOi09CajmGBGmX2XbiX6PoO6xY9nOB56zssk0QrG-68c0Se1G6ziEQaNtg31_GXGYY");
    user.setRegistrationToken("/topics/global");
    Slot slot = new Slot();
    slot.setUser(user);
    Set<Slot> slotSet = new HashSet<Slot>();
    slotSet.add(slot);

    CloudMessageSender.getInstance().sendGCM("ParkMe Message", slotSet);
  }
}
