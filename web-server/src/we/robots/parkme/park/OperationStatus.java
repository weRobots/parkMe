package we.robots.parkme.park;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("operationStatus")
public class OperationStatus
{
  @XStreamAlias("status")
  private String status;

  @XStreamAlias("failReason")
  private String message;

  public OperationStatus(OPERATION_STATUS operationStatus, String message)
  {
    this.status = operationStatus.toString();
    this.message = message;

  }

  public String getMessage()
  {
    return message;
  }

  public void setMessage(String message)
  {
    this.message = message;
  }

  public enum OPERATION_STATUS
  {
    SUCCESS, FAIL
  }

  public String getStatus()
  {
    return status;
  }

  public void setStatus(String status)
  {
    this.status = status;
  }

}
