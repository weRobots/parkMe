package we.robots.parkme.util;

import java.util.UUID;

import we.robots.parkme.user.User;

public class UserHandler
{

  private static UserHandler instance = new UserHandler();

  private UserHandler()
  {

  }

  public static UserHandler getInstance()
  {
    return instance;
  }

  public String saveUserDetails(User user)
  {
    String userId = null;
    try
    {
      UUID uuid = UUID.randomUUID();
      userId = uuid.toString();
      user.setUserId(userId);
      CommonUtil.save(Config.CAR_PARK_FILE_FOLDER, Config.CAR_PARK_FILE_PREFIX, user, userId);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return CommonUtil.toXML(user);
  }

  public String readUserDetailsAsXML(String userId)
  {
    try
    {

      return CommonUtil.read(Config.CAR_PARK_FILE_FOLDER, Config.CAR_PARK_FILE_PREFIX, userId);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return "";
  }
  
  public User readUserDetailsAsObj(String userId)
  {
    try
    {

      String xml= CommonUtil.read(Config.CAR_PARK_FILE_FOLDER, Config.CAR_PARK_FILE_PREFIX, userId);
      return CommonUtil.readObjectFromXMLForUser(xml);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    return null;
  }
}
