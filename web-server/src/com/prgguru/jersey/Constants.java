package com.prgguru.jersey;

//Change these parameters according to your DB
public class Constants {
	public static String driver = "net.sourceforge.jtds.jdbc.Driver";
	private static String dbName = "OfficeCarPark";
	public static String dbUrl = "jdbc:jtds:sqlserver://localhost:1433/"
			+ dbName;
	public static String dbUser = "sa";
	public static String dbPwd = "sa";
}
