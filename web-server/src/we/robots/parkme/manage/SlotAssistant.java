package we.robots.parkme.manage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import we.robots.parkme.park.Direction;
import we.robots.parkme.park.Slot;
import we.robots.parkme.park.SlotStatus;
import we.robots.parkme.util.CommonUtil;

public class SlotAssistant {
	private final HashMap<String, Slot> slots;

	public SlotAssistant(final HashMap<String, Slot> slots) {
		this.slots = slots;
	}

	/**
	 * @param slot
	 * @return
	 */
	public static String toId(final Slot slot) {
		return slot.getId();
	}

	/**
	 * @param slotId
	 * @return
	 */
	public Slot getSlot(final String slotId) {
		return slots.get(slotId);
	}

	/**
	 * @param slotId
	 * @return
	 */
	public Set<Slot> identifyBlockingSlots(final String slotId) {

		System.out.println("identifying blocking slots .... ");
		final Set<Slot> allPath = new HashSet<Slot>();
		final Slot slotToLeave = getSlot(slotId);

		System.out.println("analyzing slot[" + slotToLeave.getId()
				+ "] to leave");
		Slot nextImmidiateUpSlot = getImmediateUpSlot(slotToLeave);

		while (CommonUtil.checkNotNull(nextImmidiateUpSlot)) {

			CommonUtil.addSafely(allPath, nextImmidiateUpSlot);

			// Identifying up slots of, immidiateUpSlots
			final Slot immediateUpSlot = getImmediateUpSlot(nextImmidiateUpSlot);

			// Redeclare the immidiateUpSlots by new up slots
			nextImmidiateUpSlot = immediateUpSlot;
		}

		final Set<Slot> notifyNeedSlots = new HashSet<Slot>();

		// filter only allocated slots
		for (Slot slot : allPath) {
			if (slot.getStatus().equals(SlotStatus.ALLOCATED)) {
				notifyNeedSlots.add(slot);
			}
		}

		System.out.println("identified slot list to be notified..");
		for (Slot slot : notifyNeedSlots) {
			System.out.println(slot.getId());
		}

		return notifyNeedSlots;
	}

	private Slot getImmediateUpSlot(final Slot slot) {
		if ((slot == null) || (slot.getLevel() == 0)) {
			return null;
		}

		final Set<Slot> immediateUpSlots = new HashSet<Slot>();
		final HashMap<Direction, String> navigationDetail = slot
				.getNavigationDetail();
		final Slot leftUp = getSlot(navigationDetail.get(Direction.FRONT_LEFT));
		final Slot rightUp = getSlot(navigationDetail
				.get(Direction.FRONT_RIGHT));

		// if same only one will b added
		CommonUtil.addSafely(immediateUpSlots, leftUp);
		CommonUtil.addSafely(immediateUpSlots, rightUp);

		// current support only a one slot
		// return immediateUpSlots;
		if (leftUp != null) {
			return leftUp;
		}

		return null;
	}
}
