package we.robots.parkme.services;

import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import we.robots.parkme.manage.CarParkManager;
import we.robots.parkme.messaging.CloudMessageSender;
import we.robots.parkme.park.CarPark;
import we.robots.parkme.park.OperationStatus;
import we.robots.parkme.park.OperationStatus.OPERATION_STATUS;
import we.robots.parkme.park.Slot;
import we.robots.parkme.park.SlotStatus;
import we.robots.parkme.util.CarParkFileHandler;
import we.robots.parkme.util.CommonUtil;
/**
 * Opens up operations for the car park
 * @author Navod.Eranda
 *
 */
@Path("/carParkService")
public class CarParkService {

	@GET
	@Path("/parkCare")
	@Produces(MediaType.APPLICATION_XML)
	public String parkCare(@QueryParam("carParkId") String carParkId, @QueryParam("latitude") String latitude,
			@QueryParam("longitude") String longitude, @QueryParam("slotId") String slotId,
			@QueryParam("userId") String userId) {
		CarPark carPark = CommonUtil.readObjectFromXMLForCarPark(CarParkFileHandler.readCarPark(carParkId));

		if (CarParkManager.getInstance().isParkable(carPark, latitude, longitude, slotId)) {
			CarPark savedParkData = CarParkManager.getInstance().saveSlot(carPark, slotId, userId,
					SlotStatus.ALLOCATED);
			savedParkData.setOperationStatus(new OperationStatus(OPERATION_STATUS.SUCCESS, "Successfully parked car"));
			return CommonUtil.toXML(savedParkData);

		}
    carPark.setOperationStatus(new OperationStatus(OPERATION_STATUS.FAIL,
        "Fail to park car because user is not yet in the car park or Slot was occupied by another car"));
    return CommonUtil.toXML(carPark);

	}

	@GET
	@Path("/releaseCar")
	@Produces(MediaType.APPLICATION_XML)
	public String releaseCar(@QueryParam("carParkId") String carParkId, @QueryParam("userId") String userId) {
		CarPark carPark = CommonUtil.readObjectFromXMLForCarPark(CarParkFileHandler.readCarPark(carParkId));
		CarPark savedParkData = CarParkManager.getInstance().releaseCar(carPark, userId);
		savedParkData
				.setOperationStatus(new OperationStatus(OPERATION_STATUS.SUCCESS, "Removed user from parked slot"));
		return CommonUtil.toXML(savedParkData);

	}

	@GET
	@Path("/sendUpdateToBlockingSlots")
	@Produces(MediaType.APPLICATION_XML)
	public String sendUpdateToBlockingSlots(@QueryParam("carParkId") String carParkId,
			@QueryParam("userId") String userId) {
		CarPark carPark = CommonUtil.readObjectFromXMLForCarPark(CarParkFileHandler.readCarPark(carParkId));
		Slot parkedSlot = CarParkManager.getInstance().identifyParkedSlot(carPark, userId);
		Set<Slot> slotsToMove = CarParkManager.getInstance().identifySlotsToMoveTheCar(carPark, parkedSlot.getId());

		OPERATION_STATUS operationStatus = CloudMessageSender.getInstance().sendGCM("Please remove your car", slotsToMove);
		carPark.setOperationStatus(new OperationStatus(operationStatus, "Sent messages for users to remove their car(s)"));
//		CarPark savedParkData = CarParkManager.getInstance().releaseCar(carPark, userId);
//		savedParkData.setOperationStatus(new OperationStatus(status, "Removed user from parked slot"));
		return CommonUtil.toXML(carPark);
	}

	@GET
	@Path("/find")
	@Produces(MediaType.TEXT_PLAIN)
	public String findNearestCarPark(@QueryParam("lat") String lattitude, @QueryParam("log") String lognitude) {

		List<CarPark> allList = CommonUtil.readCarParkList(CarParkFileHandler.readAll());

		for (CarPark carPark : allList) {
			if (CarParkManager.getInstance().isParkableCarPark(carPark, lattitude, lognitude)) {
				return carPark.getId();
			}
		}

		return "";
	}

}
