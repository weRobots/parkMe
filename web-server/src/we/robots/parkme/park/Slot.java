package we.robots.parkme.park;

import java.util.HashMap;

import we.robots.parkme.user.User;

public class Slot {
	private int id;
	private int level;
	private SlotStatus status;
	private User user;
	private HashMap<Direction, Slot> navigationDetail;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public SlotStatus getStatus() {
		return status;
	}

	public void setStatus(SlotStatus status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public HashMap<Direction, Slot> getNavigationDetail() {
		return navigationDetail;
	}

	public void setNavigationDetail(HashMap<Direction, Slot> navigationDetail) {
		this.navigationDetail = navigationDetail;
	}

}
