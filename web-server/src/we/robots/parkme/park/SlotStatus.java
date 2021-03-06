package we.robots.parkme.park;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Interface definition which described all the possible statuses for
 * {@link Slot},
 * 
 * @author suppa
 */
@XStreamAlias("status")
public enum SlotStatus {

	// slot is free
	AVAILABLE,

	// slot is allocated to someone
	ALLOCATED,

	// can't move the vehicle out of it
	BLOCKED,
}
