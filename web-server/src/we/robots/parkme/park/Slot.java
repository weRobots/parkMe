package we.robots.parkme.park;

import java.util.HashMap;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

import we.robots.parkme.user.User;

@XStreamAlias("slot")
public class Slot {
	//
	@XStreamAlias("id")
	@XStreamAsAttribute
	private String id;

	//
	@XStreamAlias("level")
	private int level;

	//
	@XStreamAlias("status")
	private SlotStatus status;

	//
	@XStreamAlias("user")
	private User user;

	//
	@XStreamAlias("type")
	private SlotType slotType;

	//
	@XStreamAlias("navigation")
	private HashMap<Direction, String> navigationDetail;

	public String getId() {
		return id;
	}

	public void setId(final String id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(final int level) {
		this.level = level;
	}

	public SlotStatus getStatus() {
		return status;
	}

	public void setStatus(final SlotStatus status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(final User user) {
		this.user = user;
	}

	public HashMap<Direction, String> getNavigationDetail() {
		return navigationDetail;
	}

	public void setNavigationDetail(final HashMap<Direction, String> navigationDetail) {
		this.navigationDetail = navigationDetail;
	}

	public SlotType getSlotType() {
		return slotType;
	}

	public void setSlotType(SlotType slotType) {
		this.slotType = slotType;
	}
}
