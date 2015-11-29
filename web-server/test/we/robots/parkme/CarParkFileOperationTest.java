package we.robots.parkme;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.Test;

import we.robots.parkme.manage.SlotAssistant;
import we.robots.parkme.park.CarPark;
import we.robots.parkme.park.Direction;
import we.robots.parkme.park.Slot;
import we.robots.parkme.park.SlotType;
import we.robots.parkme.util.CarParkFileHandler;
import we.robots.parkme.util.CommonUtil;

public class CarParkFileOperationTest {
	@Test
	public void test_saveCarPark() {
		final CarPark officeCarPark = createOfficeCarPark();
		CarParkFileHandler.save(officeCarPark, "01");
	}

	private CarPark createOfficeCarPark() {
		final CarPark office = new CarPark();
		final HashSet<Slot> slots = new HashSet<Slot>();

		for (int i = 1; i < 19; i++) {
			final Slot slot = new Slot();
			slot.setId(Integer.toString(i));
			slot.setSlotType(SlotType.VERTICLE);
			slot.setNavigationDetail(createNewNavigateDetail());
			slots.add(slot);
		}

		// link slots
		final SlotAssistant slotAssistant = new SlotAssistant(CommonUtil.convert(slots));

		// slot 01
		final Slot slot_1 = slotAssistant.getSlot("1");
		setNabours(slot_1, 0, "3", "3", null, null, "2", "2", null, null);

		// slot 02
		final Slot slot_2 = slotAssistant.getSlot("2");
		setNabours(slot_2, 0, "4", "4", null, null, null, null, "1", "1");

		// slot 03
		final Slot slot_3 = slotAssistant.getSlot("3");
		setNabours(slot_3, 1, "5", "5", "1", "1", "4", "4", null, null);

		// slot 04
		final Slot slot_4 = slotAssistant.getSlot("4");
		setNabours(slot_4, 1, "6", "6", "2", "2", null, null, "3", "3");

		// slot 05
		final Slot slot_5 = slotAssistant.getSlot("5");
		setNabours(slot_5, 2, "9", "9", "3", "3", "6", "6", null, null);

		// slot 06
		final Slot slot_6 = slotAssistant.getSlot("6");
		setNabours(slot_6, 2, "10", "10", "4", "4", null, "7", "5", "5");

		// slot 07
		final Slot slot_7 = slotAssistant.getSlot("7");
		slot_7.setSlotType(SlotType.HORIZONTAL);
		setNabours(slot_7, 3, "8", "8", "6", "6", null, null, "11", "12");

		// slot 08
		final Slot slot_8 = slotAssistant.getSlot("8");
		slot_8.setSlotType(SlotType.HORIZONTAL);
		setNabours(slot_8, 4, null, null, "7", "7", null, null, "12", "13");

		// slot 09
		final Slot slot_9 = slotAssistant.getSlot("9");
		setNabours(slot_9, 3, "14", "14", "5", "5", "10", "10", null, null);

		// slot 10
		final Slot slot_10 = slotAssistant.getSlot("10");
		setNabours(slot_10, 3, "15", "15", "6", "6", "11", "11", "9", "9");

		// slot 11
		final Slot slot_11 = slotAssistant.getSlot("11");
		setNabours(slot_11, 4, "16", "16", "7", "7", "12", "12", "10", "10");

		// slot 12
		final Slot slot_12 = slotAssistant.getSlot("12");
		setNabours(slot_12, 5, "17", "17", "8", "7", "13", "13", "11", "11");

		// slot 13
		final Slot slot_13 = slotAssistant.getSlot("13");
		setNabours(slot_13, 5, "18", "18", "8", "8", null, null, "12", "12");

		// slot 14
		final Slot slot_14 = slotAssistant.getSlot("14");
		setNabours(slot_14, 4, null, null, "9", "9", "15", "15", null, null);

		// slot 15
		final Slot slot_15 = slotAssistant.getSlot("15");
		setNabours(slot_15, 4, null, null, "10", "10", "16", "16", "14", "14");

		// slot 16
		final Slot slot_16 = slotAssistant.getSlot("16");
		setNabours(slot_16, 5, null, null, "11", "11", "17", "17", "15", "15");

		// slot 17
		final Slot slot_17 = slotAssistant.getSlot("17");
		setNabours(slot_17, 6, null, null, "12", "12", "18", "18", "16", "16");

		// slot 18
		final Slot slot_18 = slotAssistant.getSlot("18");
		setNabours(slot_18, 6, null, null, "13", "13", null, null, "17", "17");

		office.setSlots(slots);

		return office;
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
