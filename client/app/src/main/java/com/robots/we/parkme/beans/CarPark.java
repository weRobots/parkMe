package com.robots.we.parkme.beans;

import java.util.Set;

/**
 * Defines the Car park.
 *
 * @author suppa
 */
public class CarPark {

	String id;
	String centerLocation;
	Set<Slot> slots;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getCenterLocation() {return centerLocation;}

	public void setCenterLocation(final String centerLocation) { this.centerLocation = centerLocation; }

	public Set<Slot> getSlots() {
		return slots;
	}

	public void setSlots(final Set<Slot> slots) {
		this.slots = slots;
	}
}
