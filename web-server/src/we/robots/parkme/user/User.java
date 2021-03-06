package we.robots.parkme.user;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import we.robots.parkme.common.ParkMeSaveData;

@XStreamAlias("user")
public class User implements ParkMeSaveData {
	@XStreamAlias("registrationToken")
	private String registrationToken;

	@XStreamAlias("vehicleNumber")
	private String vehichleNumber;

	@XStreamAlias("mobileNumber")
	private String mobileNumber;

	@XStreamAlias("userId")
	private String userId;

	@XStreamAlias("name")
	private String name;

	@XStreamAlias("role")
	private UserRole role;

	public User() {
		this.role = UserRole.DEFAULT;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegistrationToken() {
		return registrationToken;
	}

	public void setRegistrationToken(String registrationToken) {
		this.registrationToken = registrationToken;
	}

	public String getVehichleNumber() {
		return vehichleNumber;
	}

	public void setVehichleNumber(String vehichleNumber) {
		this.vehichleNumber = vehichleNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
