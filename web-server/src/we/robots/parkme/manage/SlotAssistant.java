package we.robots.parkme.manage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import we.robots.parkme.park.Direction;
import we.robots.parkme.park.Slot;
import we.robots.parkme.util.CommonUtil;

public class SlotAssistant {
	private final Set<Slot> slots;

	public SlotAssistant(final Set<Slot> slots) {
		this.slots = slots;
	}

	public static int toId(final Slot slot) {
		return slot.getId();
	}

	private Slot getSlot(final int slotId) {
		for (final Slot slot : slots) {
			if (slot.getId() == slotId) {
				return slot;
			}
		}

		return null;
	}

	public Set<Slot> identifyBlockingSlots(final int slotId) {
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
		final HashMap<Direction, Slot> navigationDetail = slot
				.getNavigationDetail();
		final Slot leftUp = navigationDetail.get(Direction.LEFT_FRONT);
		final Slot rightUp = navigationDetail.get(Direction.RIGHT_FRONT);

		// if same only one will b added
		CommonUtil.addSafely(immediateUpSlots, leftUp);
		CommonUtil.addSafely(immediateUpSlots, rightUp);

		return immediateUpSlots;
	}

}
