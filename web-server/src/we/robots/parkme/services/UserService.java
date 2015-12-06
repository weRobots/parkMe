package we.robots.parkme.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import we.robots.parkme.user.User;
import we.robots.parkme.user.UserRole;
import we.robots.parkme.util.UserHandler;

@Path("/userService")
public class UserService
{
  @GET
  @Path("/saveUser")
  @Produces(MediaType.APPLICATION_XML)
  public String saveUser(@QueryParam("registrationToken")
  String registrationToken, @QueryParam("vehichleNumber")
  String vehichleNumber, @QueryParam("mobileNumber")
  String mobileNumber, @QueryParam("name")
  String name)
  {
    User user = new User();
    user.setMobileNumber(mobileNumber);
    user.setName(name);
    user.setRegistrationToken(registrationToken);
    user.setVehichleNumber(vehichleNumber);

    return UserHandler.getInstance().saveUserDetails(user);
  }

  @GET
  @Path("/readUser")
  @Produces(MediaType.APPLICATION_XML)
  public String readUser(@QueryParam("userId")
  String userId)
  {
    return UserHandler.getInstance().readUserDetailsAsXML(userId);
  }

  @GET
  @Path("/createAdminUser")
  @Produces(MediaType.APPLICATION_XML)
  public String createAdminUser(@QueryParam("registrationToken")
  String registrationToken, @QueryParam("vehichleNumber")
  String vehichleNumber, @QueryParam("mobileNumber")
  String mobileNumber, @QueryParam("name")
  String name)
  {
    User user = new User();
    user.setMobileNumber(mobileNumber);
    user.setName(name);
    user.setRegistrationToken(registrationToken);
    user.setVehichleNumber(vehichleNumber);
    user.setRole(UserRole.ADMIN);
    return UserHandler.getInstance().saveUserDetails(user);
  }

  @GET
  @Path("/updateToken")
  @Produces(MediaType.APPLICATION_XML)
  public String updateToken(@QueryParam("registrationToken")
  String registrationToken, @QueryParam("userId")
  String userId)
  {
    User user = UserHandler.getInstance().readUserDetailsAsObj(userId);
    user.setRegistrationToken(registrationToken);
    return UserHandler.getInstance().saveUserDetails(user);
  }
}
