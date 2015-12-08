package we.robots.parkme.services;

import java.util.List;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import we.robots.parkme.manage.CarParkManager;
import we.robots.parkme.messaging.gsm.CloudMessageSender;
import we.robots.parkme.messaging.mms.MMSMessageHandler;
import we.robots.parkme.park.CarPark;
import we.robots.parkme.park.OperationStatus;
import we.robots.parkme.park.OperationStatus.OPERATION_STATUS;
import we.robots.parkme.park.Slot;
import we.robots.parkme.park.SlotStatus;
import we.robots.parkme.util.CarParkFileHandler;
import we.robots.parkme.util.CommonUtil;

/**
 * Opens up operations for the car park
 * 
 * @author Navod.Eranda
 *
 */
@Path("/carParkService")
public class CarParkService {

	@GET
	@Path("/parkCare")
	@Produces(MediaType.APPLICATION_XML)
	public String parkCare(@QueryParam("carParkId") String carParkId,
			@QueryParam("latitude") String latitude,
			@QueryParam("longitude") String longitude,
			@QueryParam("slotId") String slotId,
			@QueryParam("userId") String userId) {

		//
		System.out.println("allocate process started...");

		CarPark carPark = CommonUtil
				.readObjectFromXMLForCarPark(CarParkFileHandler
						.readCarPark(carParkId));

		if (CarParkManager.getInstance().isParkable(carPark, latitude,
				longitude, slotId)) {

			//
			System.out.println("location is valid to allocate .. ");

			CarPark savedParkData = CarParkManager.getInstance().saveSlot(
					carPark, slotId, userId, SlotStatus.ALLOCATED);
			savedParkData.setOperationStatus(new OperationStatus(
					OPERATION_STATUS.SUCCESS, "Successfully parked car"));

			System.out.println(carPark.getId()
					+ "[ Successfully parked car for the user: " + userId
					+ "] slot: " + slotId);
			return CommonUtil.toXML(savedParkData);
		}

		carPark.setOperationStatus(new OperationStatus(
				OPERATION_STATUS.FAIL,
				"Fail to park car because user is not yet in the car park or Slot was occupied by another car"));
		System.out.println(carPark.getId()
				+ "[ allocating operation fails, for the user: " + userId
				+ "] slot: " + slotId + " lat: " + latitude + " long: "
				+ longitude);

		return CommonUtil.toXML(carPark);
	}

	@GET
	@Path("/releaseCar")
	@Produces(MediaType.APPLICATION_XML)
	public String releaseCar(@QueryParam("carParkId") String carParkId,
			@QueryParam("slotId") String slotId,
			@QueryParam("userId") String userId) {

		//
		System.out.println("release process started...");
		CarPark carPark = CommonUtil
				.readObjectFromXMLForCarPark(CarParkFileHandler
						.readCarPark(carParkId));

		CarPark savedParkData = CarParkManager.getInstance().releaseCar(
				carPark, slotId);
		savedParkData.setOperationStatus(new OperationStatus(
				OPERATION_STATUS.SUCCESS, "Removed user from parked slot"));
		return CommonUtil.toXML(savedParkData);
	}

	@GET
	@Path("/sendUpdateToBlockingSlots")
	@Produces(MediaType.APPLICATION_XML)
	public String sendUpdateToBlockingSlots(
			@QueryParam("carParkId") String carParkId,
			@QueryParam("slotId") String slotId,
			@QueryParam("userId") String userId) {

		//
		System.out
				.println("sending notification to blockers started for the park["
						+ carParkId
						+ "] slot["
						+ slotId
						+ "] user["
						+ userId
						+ "]");

		CarPark carPark = CommonUtil
				.readObjectFromXMLForCarPark(CarParkFileHandler
						.readCarPark(carParkId));

		Set<Slot> slotsToMove = CarParkManager.getInstance()
				.identifySlotsToMoveTheCar(carPark, slotId);

		// data notification to blocking users
		System.out.println("sending cloud messages..");
		OPERATION_STATUS operationStatus = CloudMessageSender.getInstance()
				.sendGCM("Please move your car out", slotsToMove);
		carPark.setOperationStatus(new OperationStatus(operationStatus,
				"Sent messages for users to remove their car(s)"));

		// MMS message to blocking users
		MMSMessageHandler.sendNotifications(slotsToMove,
				MMSMessageHandler.NOTIFICATION_TYPE_REMOVE_YOUR_CAR);

		return CommonUtil.toXML(carPark);
	}

	@GET
	@Path("/find")
	@Produces(MediaType.TEXT_PLAIN)
	public String findNearestCarPark(@QueryParam("lat") String lattitude,
			@QueryParam("log") String lognitude) {

		List<CarPark> allList = CommonUtil.readCarParkList(CarParkFileHandler
				.readAll());

		for (CarPark carPark : allList) {
			if (CarParkManager.getInstance().isParkableCarPark(carPark,
					lattitude, lognitude)) {
				return carPark.getId();
			}
		}

		return "";
	}

	@GET
	@Path("/block")
	@Produces(MediaType.APPLICATION_XML)
	public String blockSlot(@QueryParam("carParkId") String carParkId,
			@QueryParam("slotId") String slotId,
			@QueryParam("userId") String userId) {

		CarPark carPark = CommonUtil
				.readObjectFromXMLForCarPark(CarParkFileHandler
						.readCarPark(carParkId));
		CarPark savedParkData = CarParkManager.getInstance().block(carPark,
				slotId);
		savedParkData.setOperationStatus(new OperationStatus(
				OPERATION_STATUS.SUCCESS, "Blocked the slot"));
		return CommonUtil.toXML(savedParkData);

	}

}
