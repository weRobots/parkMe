package we.robots.parkme.messaging;

import java.util.HashSet;
import java.util.Set;

import we.robots.parkme.park.Slot;
import we.robots.parkme.user.User;

public class CloudMessageSenderTest
{

  public static void main (String [] args){
    User user = new User ();
    user.setRegistrationToken("");
    
    Slot slot = new Slot ();
    slot.setUser(user);
    Set<Slot> slotSet = new HashSet<Slot>();
    slotSet.add(slot);
    
    
    CloudMessageSender.getInstance().sendGCM("Message", slotSet);
  }
}
