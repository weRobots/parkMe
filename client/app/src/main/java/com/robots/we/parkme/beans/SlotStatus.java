package com.robots.we.parkme.beans;

/**
 * Interface definition which described all the possible statuses for
 * {@link Slot},
 * 
 * @author suppa
 */
public enum SlotStatus {

	// slot is free
	AVAILABLE,

	// slot is allocated to someone
	ALLOCATED,

	// can't move the vehicle out of it
	BLOCKED,
}
