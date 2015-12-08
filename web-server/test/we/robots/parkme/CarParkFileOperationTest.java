package we.robots.parkme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import we.robots.parkme.manage.SlotAssistant;
import we.robots.parkme.park.CarPark;
import we.robots.parkme.park.Direction;
import we.robots.parkme.park.Slot;
import we.robots.parkme.park.SlotStatus;
import we.robots.parkme.park.SlotType;
import we.robots.parkme.user.User;
import we.robots.parkme.user.UserRole;
import we.robots.parkme.util.CarParkFileHandler;
import we.robots.parkme.util.CommonUtil;

public class CarParkFileOperationTest {
	@Test
	public void test_saveCarPark() {
		final CarPark officeCarPark = createOfficeCarPark();
		CarParkFileHandler.save(officeCarPark);
	}

	@Test
	public void test_saveCarPark_09() {
		final CarPark office_09_CarPark = createOfficeCarPark_09();
		CarParkFileHandler.save(office_09_CarPark);
	}

	@Test
	public void test_all_configured() {

		List<CarPark> all_configured = new ArrayList<CarPark>();

		final CarPark office_main = new CarPark();
		office_main.setId("cambio_main");
		office_main.setAdmin(createAdmin("admin_01", "cambio_29_admin"));
		office_main.setCenterLocationLat("6.907062");
		office_main.setCenterLocationLog("79.853669");
		all_configured.add(office_main);

		//
		final CarPark office_09 = new CarPark();
		office_09.setId("cambio_09");
		office_09.setAdmin(createAdmin("admin_02", "cambio_09_admin"));
		office_09.setCenterLocationLat("6.9068317");
		office_09.setCenterLocationLog("6.9068317");
		all_configured.add(office_09);

		CarParkFileHandler.saveAllConfigured(all_configured);
	}

	private CarPark createOfficeCarPark_09() {

		final CarPark office = new CarPark();
		office.setId("cambio_09");
		office.setAdmin(createAdmin("admin_02", "cambio_09_admin"));
		office.setCenterLocationLat("6.9068317");
		office.setCenterLocationLog("79.8529107");
		final HashSet<Slot> slots = new HashSet<Slot>();

		for (int i = 1; i < 13; i++) {
			final Slot slot = new Slot();
			slot.setId(Integer.toString(i));
			slot.setSlotType(SlotType.VERTICAL);
			slot.setNavigationDetail(createNewNavigateDetail());
			slot.setStatus(SlotStatus.AVAILABLE);
			slots.add(slot);
		}

		// link slots
		final SlotAssistant slotAssistant = new SlotAssistant(CommonUtil.convert(slots));

		// slot 01
		final Slot slot_1 = slotAssistant.getSlot("1");
		slot_1.setColumnIndex(3);
		slot_1.setRawIndex(0);
		setNabours(slot_1, 0, "3", "3", null, null, "2", "2", null, null);

		// slot 02
		final Slot slot_2 = slotAssistant.getSlot("2");
		slot_2.setColumnIndex(2);
		slot_2.setRawIndex(0);
		setNabours(slot_2, 0, "4", "4", null, null, null, null, "1", "1");

		// slot 03
		final Slot slot_3 = slotAssistant.getSlot("3");
		slot_3.setColumnIndex(3);
		slot_3.setRawIndex(2);
		setNabours(slot_3, 1, "5", "5", "1", "1", "4", "4", null, null);

		// slot 04
		final Slot slot_4 = slotAssistant.getSlot("4");
		slot_4.setColumnIndex(2);
		slot_4.setRawIndex(2);
		setNabours(slot_4, 1, "6", "6", "2", "2", null, null, "3", "3");

		// slot 05
		final Slot slot_5 = slotAssistant.getSlot("5");
		slot_5.setColumnIndex(3);
		slot_5.setRawIndex(4);
		setNabours(slot_5, 2, "9", "9", "3", "3", "6", "6", null, null);

		// slot 06
		final Slot slot_6 = slotAssistant.getSlot("6");
		slot_6.setColumnIndex(2);
		slot_6.setRawIndex(4);
		setNabours(slot_6, 2, "10", "10", "4", "4", "7", "8", "5", "5");

		// slot 07
		final Slot slot_7 = slotAssistant.getSlot("7");
		slot_7.setSlotType(SlotType.HORIZONTAL);
		slot_7.setColumnIndex(0);
		slot_7.setRawIndex(4);
		setNabours(slot_7, 3, null, null, "6", "6", null, null, "8", "8");

		// slot 08
		final Slot slot_8 = slotAssistant.getSlot("8");
		slot_8.setSlotType(SlotType.HORIZONTAL);
		slot_8.setColumnIndex(0);
		slot_8.setRawIndex(5);
		setNabours(slot_8, 3, null, null, "6", "6", "7", "7", "11", "11");

		// slot 09
		final Slot slot_9 = slotAssistant.getSlot("9");
		slot_9.setColumnIndex(3);
		slot_9.setRawIndex(6);
		setNabours(slot_9, 3, null, null, "5", "5", "10", "10", null, null);

		// slot 10
		final Slot slot_10 = slotAssistant.getSlot("10");
		slot_10.setColumnIndex(2);
		slot_10.setRawIndex(6);
		setNabours(slot_10, 3, null, null, "6", "6", "11", "12", "9", "9");	

		// slot 11
		final Slot slot_11 = slotAssistant.getSlot("11");
		slot_11.setSlotType(SlotType.HORIZONTAL);
		slot_11.setColumnIndex(0);
		slot_11.setRawIndex(6);
		setNabours(slot_11, 4, null, null, "10", "10", "8", "8", "12", "12");

		// slot 12
		final Slot slot_12 = slotAssistant.getSlot("12");
		slot_12.setSlotType(SlotType.HORIZONTAL);
		slot_12.setColumnIndex(0);
		slot_12.setRawIndex(7);
		setNabours(slot_12, 4, null, null, "10", "10", "11", "11", null, null);

		office.setSlots(slots);

		return office;

	}

	private CarPark createOfficeCarPark() {
		final CarPark office = new CarPark();
		office.setId("cambio_main");
		office.setAdmin(createAdmin("admin_01", "cambio_29_admin"));
		office.setCenterLocationLat("34.9068317");
		office.setCenterLocationLog("6.8529107");
		final HashSet<Slot> slots = new HashSet<Slot>();

		for (int i = 1; i < 21; i++) {
			final Slot slot = new Slot();
			slot.setId(Integer.toString(i));
			slot.setSlotType(SlotType.VERTICAL);
			slot.setNavigationDetail(createNewNavigateDetail());
			slot.setStatus(SlotStatus.AVAILABLE);
			slots.add(slot);
		}

		// link slots
		final SlotAssistant slotAssistant = new SlotAssistant(CommonUtil.convert(slots));

		// slot 01
		final Slot slot_1 = slotAssistant.getSlot("1");
		slot_1.setColumnIndex(5);
		slot_1.setRawIndex(0);
		setNabours(slot_1, 0, "3", "3", null, null, "2", "2", null, null);

		// slot 02
		final Slot slot_2 = slotAssistant.getSlot("2");
		slot_2.setColumnIndex(4);
		slot_2.setRawIndex(0);
		setNabours(slot_2, 0, "4", "4", null, null, null, null, "1", "1");

		// slot 03
		final Slot slot_3 = slotAssistant.getSlot("3");
		slot_3.setColumnIndex(5);
		slot_3.setRawIndex(2);
		setNabours(slot_3, 1, "5", "5", "1", "1", "4", "4", null, null);

		// slot 04
		final Slot slot_4 = slotAssistant.getSlot("4");
		slot_4.setColumnIndex(4);
		slot_4.setRawIndex(2);
		setNabours(slot_4, 1, "6", "6", "2", "2", null, null, "3", "3");

		// slot 05
		final Slot slot_5 = slotAssistant.getSlot("5");
		slot_5.setColumnIndex(5);
		slot_5.setRawIndex(4);
		setNabours(slot_5, 2, "9", "9", "3", "3", "6", "6", null, null);

		// slot 06
		final Slot slot_6 = slotAssistant.getSlot("6");
		slot_6.setColumnIndex(4);
		slot_6.setRawIndex(4);
		setNabours(slot_6, 2, "10", "10", "4", "4", null, "7", "5", "5");

		// slot 07
		final Slot slot_7 = slotAssistant.getSlot("7");
		slot_7.setSlotType(SlotType.HORIZONTAL);
		slot_7.setColumnIndex(2);
		slot_7.setRawIndex(5);
		setNabours(slot_7, 3, "8", "8", "6", "6", null, null, "11", "12");

		// slot 08
		final Slot slot_8 = slotAssistant.getSlot("8");
		slot_8.setSlotType(SlotType.HORIZONTAL);
		slot_8.setColumnIndex(0);
		slot_8.setRawIndex(5);
		setNabours(slot_8, 4, null, null, "7", "7", null, null, "12", "13");

		// slot 09
		final Slot slot_9 = slotAssistant.getSlot("9");
		slot_9.setColumnIndex(5);
		slot_9.setRawIndex(6);
		setNabours(slot_9, 3, "14", "14", "5", "5", "10", "10", null, null);

		// slot 10
		final Slot slot_10 = slotAssistant.getSlot("10");
		slot_10.setColumnIndex(4);
		slot_10.setRawIndex(6);
		setNabours(slot_10, 3, "15", "15", "6", "6", "11", "11", "9", "9");

		// slot 11
		final Slot slot_11 = slotAssistant.getSlot("11");
		slot_11.setColumnIndex(3);
		slot_11.setRawIndex(6);
		setNabours(slot_11, 4, "16", "16", "7", "7", "12", "12", "10", "10");

		// slot 12
		final Slot slot_12 = slotAssistant.getSlot("12");
		slot_12.setColumnIndex(2);
		slot_12.setRawIndex(6);
		setNabours(slot_12, 5, "17", "17", "8", "7", "13", "13", "11", "11");

		// slot 13
		final Slot slot_13 = slotAssistant.getSlot("13");
		slot_13.setColumnIndex(1);
		slot_13.setRawIndex(6);
		setNabours(slot_13, 5, "18", "18", "8", "8", null, null, "12", "12");

		// slot 14
		final Slot slot_14 = slotAssistant.getSlot("14");
		slot_14.setColumnIndex(0);
		slot_14.setRawIndex(6);
		setNabours(slot_14, 5, "20", "20", "8", "8", null, null, "13", "13");

		// slot 15
		final Slot slot_15 = slotAssistant.getSlot("15");
		slot_15.setColumnIndex(5);
		slot_15.setRawIndex(8);
		setNabours(slot_15, 4, null, null, "9", "9", "16", "16", null, null);

		// slot 16
		final Slot slot_16 = slotAssistant.getSlot("16");
		slot_16.setColumnIndex(4);
		slot_16.setRawIndex(8);
		setNabours(slot_16, 4, null, null, "10", "10", "17", "17", "15", "15");

		// slot 17
		final Slot slot_17 = slotAssistant.getSlot("17");
		slot_17.setColumnIndex(3);
		slot_17.setRawIndex(8);
		setNabours(slot_17, 5, null, null, "11", "11", "18", "18", "16", "16");

		// slot 18
		final Slot slot_18 = slotAssistant.getSlot("18");
		slot_18.setColumnIndex(2);
		slot_18.setRawIndex(8);
		setNabours(slot_18, 5, null, null, "12", "12", "19", "19", "17", "17");

		// slot 18
		final Slot slot_19 = slotAssistant.getSlot("19");
		slot_19.setColumnIndex(1);
		slot_19.setRawIndex(8);
		setNabours(slot_19, 6, null, null, "13", "13", "20", "20", "18", "18");

		// slot 18
		final Slot slot_20 = slotAssistant.getSlot("20");
		slot_20.setColumnIndex(0);
		slot_20.setRawIndex(8);
		setNabours(slot_20, 6, null, null, "14", "14", null, null, "19", "19");

		office.setSlots(slots);

		return office;
	}

	private User createAdmin(String id, String name) {
		User admin = new User();
		admin.setMobileNumber("0776373232");
		admin.setName(name);
		admin.setRole(UserRole.ADMIN);
		admin.setUserId(id);
		return null;
	}

	private HashMap<Direction, String> createNewNavigateDetail() {
		return new HashMap<Direction, String>();
	}

	private void setNabours(final Slot slot, final int level, final String bl, final String br, final String fl,
			final String fr, final String lf, final String lb, final String rf, final String rb) {
		slot.setLevel(level);

		final HashMap<Direction, String> detail = slot.getNavigationDetail();

		if (bl != null) {
			detail.put(Direction.BACK_LEFT, bl);
		}

		if (br != null) {
			detail.put(Direction.BACK_RIGHT, br);
		}

		if (lb != null) {
			detail.put(Direction.LEFT_BACK, lb);
		}

		if (lf != null) {
			detail.put(Direction.LEFT_FRONT, lf);
		}

		if (fl != null) {
			detail.put(Direction.FRONT_LEFT, fl);
		}

		if (fr != null) {
			detail.put(Direction.FRONT_RIGHT, fr);
		}

		if (rf != null) {
			detail.put(Direction.RIGHT_FRONT, rf);
		}

		if (rb != null) {
			detail.put(Direction.RIGHT_BACK, rb);
		}
	}
}
