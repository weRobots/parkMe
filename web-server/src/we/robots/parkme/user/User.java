package we.robots.parkme.user;

import we.robots.parkme.common.ParkMeSaveData;

public class User implements ParkMeSaveData
{
  private String registrationToken;

  private String vehichleNumber;

  private String mobileNumber;

  private String userId;

  private String name;

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getRegistrationToken()
  {
    return registrationToken;
  }

  public void setRegistrationToken(String registrationToken)
  {
    this.registrationToken = registrationToken;
  }

  public String getVehichleNumber()
  {
    return vehichleNumber;
  }

  public void setVehichleNumber(String vehichleNumber)
  {
    this.vehichleNumber = vehichleNumber;
  }

  public String getMobileNumber()
  {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber)
  {
    this.mobileNumber = mobileNumber;
  }

  public String getUserId()
  {
    return userId;
  }

  public void setUserId(String userId)
  {
    this.userId = userId;
  }

}
