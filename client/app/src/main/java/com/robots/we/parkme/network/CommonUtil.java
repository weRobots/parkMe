package com.robots.we.parkme.network;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import we.robots.parkme.park.Slot;

/**
 * @author supun.hettigoda
 */
public final class CommonUtil {
	/**
	 * @param value
	 *
	 * @return
	 */
	public static boolean checkNotNullAndNotEmpty(final Collection<?> value) {
		return ((value != null) && !value.isEmpty());
	}

	/**
	 * @param value
	 *
	 * @return
	 */
	public static boolean checkNotNull(final Object value) {
		return (value != null);
	}

	/**
	 * @param set
	 * @param element
	 */
	public static void addSafely(final Set<Slot> set, final Slot element) {
		if (checkNotNull(set)) {
			if (checkNotNull(element)) {
				set.add(element);
			}
		}

		throw new IllegalArgumentException(
				"arguments are not valid for the operation");
	}

	/**
	 * @param set
	 * @param elements
	 */
	public static void addAllSafely(final Set<Slot> set,
			final Set<Slot> elements) {
		if (checkNotNull(set)) {
			if (checkNotNullAndNotEmpty(elements)) {
				set.addAll(elements);
			}
		}

		throw new IllegalArgumentException(
				"arguments are not valid for the operation");
	}

	/**
	 * @param slots
	 * @return
	 */
	public static HashMap<String, Slot> convert(Set<Slot> slots) {
		HashMap<String, Slot> map = new HashMap<String, Slot>();

		for (Slot slot : slots) {
			map.put(slot.getId(), slot);
		}

		return map;
	}
}
