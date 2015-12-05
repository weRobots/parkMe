package we.robots.parkme.messaging.mms;

public interface MMS {
	public static final String FROM_ADDRESS = "ADMINISTRATOR.CADD.TEAM8";

	public static final String MMS_DOMAIN_DIALOG = "@mms.dialog.lk";

	public static final String MMS_DOMAIN_MOBITEL = "@mms.mobitel.lk";

	public static final String SERVICE_PROVIDER_MOBITEL = "71";

	public static final String SERVICE_PROVIDER_DIALOG = "77";

	public static final String TELE_CODE_SRL = "94";

	public static final int MESSAGE_TYPE_REMOVE_YOUR_CAR = 1;

	String getToAddress();

	String getSubject();

	String getText();

}
