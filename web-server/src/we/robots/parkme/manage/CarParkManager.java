package we.robots.parkme.manage;

import java.util.Set;

import we.robots.parkme.park.CarPark;
import we.robots.parkme.park.Slot;
import we.robots.parkme.park.SlotStatus;
import we.robots.parkme.user.User;
import we.robots.parkme.util.CarParkFileHandler;
import we.robots.parkme.util.CommonUtil;
import we.robots.parkme.util.DistanceCalculator;
import we.robots.parkme.util.UserHandler;

public class CarParkManager {
	private static final Double MAX_PARKABLE_DISTANCE = 50d;

	private static CarParkManager instance = new CarParkManager();

	private CarParkManager() {

	}

	public static CarParkManager getInstance() {
		return instance;
	}

	private boolean isSlotAvailableForParking(CarPark carPark, String slotId) {
		for (Slot slot : carPark.getSlots()) {
			if (slot.getId().equals(slotId)
					&& SlotStatus.AVAILABLE.equals(slot.getStatus())) {
				return true;
			}
		}
		return false;
	}

	public CarPark saveSlot(CarPark carPark, String slotId, String userId,
			SlotStatus slotStatus) {

		User user = UserHandler.getInstance().readUserDetailsAsObj(userId);

		SlotAssistant slotAssistant = new SlotAssistant(
				CommonUtil.convert(carPark.getSlots()));
		Slot slot = slotAssistant.getSlot(slotId);

		slot.setStatus(slotStatus);
		slot.setUser(user);

		CarParkFileHandler.save(carPark);
		return carPark;
	}

	public CarPark releaseCar(CarPark carPark, String slotId) {
		SlotAssistant slotAssistant = new SlotAssistant(
				CommonUtil.convert(carPark.getSlots()));
		Slot slot = slotAssistant.getSlot(slotId);
		slot.setStatus(SlotStatus.AVAILABLE);
		slot.setUser(null);
		//
		System.out.println("Slot: " + slot.getId() + "released success");

		CarParkFileHandler.save(carPark);
		return carPark;
	}

	public CarPark block(CarPark carPark, String slotId) {
		SlotAssistant slotAssistant = new SlotAssistant(
				CommonUtil.convert(carPark.getSlots()));
		Slot slot = slotAssistant.getSlot(slotId);
		slot.setStatus(SlotStatus.BLOCKED);
		slot.setUser(null);
		//
		System.out.println("Slot: " + slot.getId() + "Blocked success");
		CarParkFileHandler.save(carPark);
		return carPark;
	}

	/**
	 * Check if CarPark is near the user and if the slot user trying to park is
	 * unoccupied.
	 * 
	 * @param carPark
	 * @param lat
	 * @param longi
	 * @param slotId
	 * @return
	 */
	public boolean isParkable(CarPark carPark, String lat, String longi,
			String slotId) {

		return isParkableCarPark(carPark, lat, longi)
				&& isSlotAvailableForParking(carPark, slotId);
	}

	public boolean isParkableCarPark(CarPark carPark, String lat, String longi) {
		double distance = DistanceCalculator.distance(
				Double.valueOf(carPark.getCenterLocationLat()),
				Double.valueOf(carPark.getCenterLocationLog()),
				Double.valueOf(lat), Double.valueOf(longi));

		if (distance < MAX_PARKABLE_DISTANCE) {
			System.out.println("parakable result: success");
			return true;
		}

		System.out.println("parakable result: unsuccessful distance["
				+ distance + "] for parck[lat:"
				+ carPark.getCenterLocationLat() + " long:"
				+ carPark.getCenterLocationLog() + " id: " + carPark.getId()
				+ "]" + " for user lat: " + lat + " long: " + longi);

		return false;
	}

	public Set<Slot> identifySlotsToMoveTheCar(CarPark carPark, String slotId) {
		SlotAssistant slotAssistant = new SlotAssistant(
				CommonUtil.convert(carPark.getSlots()));
		Set<Slot> slotSet = slotAssistant.identifyBlockingSlots(slotId);

		return slotSet;
	}
}
