package we.robots.parkme.manage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import we.robots.parkme.park.Direction;
import we.robots.parkme.park.Slot;
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
		final Set<Slot> slotsToMove = new HashSet<Slot>();
		final Slot slotToLeave = getSlot(slotId);
		Set<Slot> immidiateUpSlots = getImmediateUpSlots(slotToLeave);

		while (CommonUtil.checkNotNullAndNotEmpty(immidiateUpSlots)) {
			CommonUtil.addAllSafely(slotsToMove, immidiateUpSlots);

			// Identifying up slots of, immidiateUpSlots
			final Set<Slot> nextImmediateUpSlots = new HashSet<Slot>();

			for (final Slot slot : immidiateUpSlots) {
				CommonUtil.addAllSafely(nextImmediateUpSlots,
						getImmediateUpSlots(slot));
			}

			// Redeclare the immidiateUpSlots by new up slots
			immidiateUpSlots = nextImmediateUpSlots;
		}

		return slotsToMove;
	}

	private Set<Slot> getImmediateUpSlots(final Slot slot) {
		if ((slot == null) || (slot.getLevel() == 0)) {
			return null;
		}

		final Set<Slot> immediateUpSlots = new HashSet<Slot>();
		final HashMap<Direction, String> navigationDetail = slot
				.getNavigationDetail();
		final Slot leftUp = getSlot(navigationDetail.get(Direction.LEFT_FRONT));
		final Slot rightUp = getSlot(navigationDetail
				.get(Direction.RIGHT_FRONT));

		// if same only one will b added
		CommonUtil.addSafely(immediateUpSlots, leftUp);
		CommonUtil.addSafely(immediateUpSlots, rightUp);

		return immediateUpSlots;
	}
}
