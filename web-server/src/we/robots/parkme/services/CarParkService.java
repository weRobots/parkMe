package we.robots.parkme.services;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import we.robots.parkme.manage.CarParkManager;
import we.robots.parkme.park.CarPark;
import we.robots.parkme.park.OperationStatus;
import we.robots.parkme.park.SlotStatus;
import we.robots.parkme.park.OperationStatus.OPERATION_STATUS;
import we.robots.parkme.util.CarParkFileHandler;
import we.robots.parkme.util.CommonUtil;

@Path("/carParkService")
public class CarParkService
{

  @GET
  @Path("/parkCare")
  @Produces(MediaType.APPLICATION_XML)
  public String parkCare(@QueryParam("carParkId")
  String carParkId, @QueryParam("latitude")
  String latitude, @QueryParam("longitude")
  String longitude, @QueryParam("slotId")
  String slotId, @QueryParam("userId")
  String userId)
  {
    CarPark carPark = CommonUtil.readObjectFromXMLForCarPark(CarParkFileHandler.read(carParkId));

    if (CarParkManager.getInstance().isParkableCarPark(carPark, latitude, longitude))
    {
      CarPark savedParkData = CarParkManager.getInstance().saveSlot(carPark, slotId, userId, SlotStatus.ALLOCATED);
      savedParkData.setOperationStatus(new OperationStatus(OPERATION_STATUS.SUCCESS, "Successfully park car"));
      return CommonUtil.toXML(savedParkData);

    }
    carPark.setOperationStatus(new OperationStatus(OPERATION_STATUS.FAIL,
        "Fail to park car because user is not yet in the car park"));
    return CommonUtil.toXML(carPark);

  }

  @GET
  @Path("/releaseCar")
  @Produces(MediaType.APPLICATION_XML)
  public String releaseCar(@QueryParam("carParkId")
  String carParkId, @QueryParam("userId")
  String userId)
  {
    CarPark carPark = CommonUtil.readObjectFromXMLForCarPark(CarParkFileHandler.read(carParkId));

    CarPark savedParkData = CarParkManager.getInstance().releaseCar(carPark, userId);
    savedParkData.setOperationStatus(new OperationStatus(OPERATION_STATUS.SUCCESS, "Removed user from parked slot"));
    return CommonUtil.toXML(savedParkData);

  }
}
