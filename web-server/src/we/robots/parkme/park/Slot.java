package we.robots.parkme.park;

import java.util.HashMap;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import we.robots.parkme.user.User;

@XStreamAlias("slot")
public class Slot {
	//
	@XStreamAlias("id")
	private String id;

	//
	@XStreamAlias("level")
	private int level;
	
	//
	@XStreamAlias("columnIndex")
    private int columnIndex;

	//
	@XStreamAlias("rawIndex")
    private int rawIndex;

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

	public int getColumnIndex() {
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}

	public int getRawIndex() {
		return rawIndex;
	}

	public void setRawIndex(int rawIndex) {
		this.rawIndex = rawIndex;
	}
	
	
}
