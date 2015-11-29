package we.robots.parkme.park;

import java.util.Set;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import we.robots.parkme.user.Admin;

/**
 * Defines the Car park.
 *
 * @author suppa
 */
@XStreamAlias("carpark")
public class CarPark {
	//
	@XStreamAlias("id")
	@XStreamAsAttribute
	String id;

	//
	@XStreamAlias("center")
	String centerLocation;

	//
	@XStreamAlias("admin")
	Admin admin;

	//
	@XStreamAlias("slotList")
	Set<Slot> slots;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public String getCenterLocation() {
		return centerLocation;
	}

	public void setCenterLocation(final String centerLocation) {
		this.centerLocation = centerLocation;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(final Admin admin) {
		this.admin = admin;
	}

	public Set<Slot> getSlots() {
		return slots;
	}

	public void setSlots(final Set<Slot> slots) {
		this.slots = slots;
	}
}
