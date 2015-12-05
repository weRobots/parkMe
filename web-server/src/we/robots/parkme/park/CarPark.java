package we.robots.parkme.park;

import java.util.Set;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import we.robots.parkme.user.Admin;

/**
 * Defines the Car park.
 *
 * @author suppa
 */
@XStreamAlias("carpark")
public class CarPark
{

  //
  @XStreamAlias("id")
  String id;

  //
  @XStreamAlias("center_lat")
  String centerLocationLat;

  @XStreamAlias("center_log")
  String centerLocationLog;

  //
  @XStreamAlias("admin")
  Admin admin;

  //
  @XStreamAlias("slots")
  Set<Slot> slots;
  
  @XStreamAlias("operationStatus")
  OperationStatus operationStatus;
    

  public OperationStatus getOperationStatus()
  {
    return operationStatus;
  }

  public void setOperationStatus(OperationStatus operationStatus)
  {
    this.operationStatus = operationStatus;
  }

  public String getId()
  {
    return id;
  }

  public void setId(final String id)
  {
    this.id = id;
  }
 

  public String getCenterLocationLat()
  {
    return centerLocationLat;
  }

  public void setCenterLocationLat(String centerLocationLat)
  {
    this.centerLocationLat = centerLocationLat;
  }

  public String getCenterLocationLog()
  {
    return centerLocationLog;
  }

  public void setCenterLocationLog(String centerLocationLog)
  {
    this.centerLocationLog = centerLocationLog;
  }

  public Admin getAdmin()
  {
    return admin;
  }

  public void setAdmin(final Admin admin)
  {
    this.admin = admin;
  }

  public Set<Slot> getSlots()
  {
    return slots;
  }

  public void setSlots(final Set<Slot> slots)
  {
    this.slots = slots;
  }
}
