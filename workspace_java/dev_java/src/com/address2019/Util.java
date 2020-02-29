package com.address2019;
import java.io.*;
import java.util.*;
import java.sql.*;

public class Util {
    //KSC5601 code page --> uni code 8859_1 convert
	public static String k2u(String Ksc)  throws UnsupportedEncodingException {
		if ( Ksc == null ) {
				return null;
		}

		return new String( Ksc.getBytes("KSC5601"), "8859_1" );
	}

    //uni code 8859_1 --> KSC5601 code page convert
	public static String u2k( String Uni ) throws UnsupportedEncodingException{
		if ( Uni == null ) {
			return null;
		}

		return new String( Uni.getBytes("8859_1"), "KSC5601" );
	}

	public static String getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int mon = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int min = cal.get(Calendar.MINUTE);
		int sec = cal.get(Calendar.SECOND);

		return	year + "" +
				(mon < 10 ? "0" + mon : "" + mon) +
				(day < 10 ? "0" + day : "" + day) +
				(hour < 10 ? "0" + hour : "" + hour) +
				(min < 10 ? "0" + min : "" + min) +
				(sec < 10 ? "0" + sec : "" + sec);

	}

	public static String getCurrentDate(String dt) {
		try {
			return	dt.substring(0, 4) + "�� " +
					dt.substring(4, 6) + "��" +
					dt.substring(6, 8) + "��" +
					dt.substring(8, 10) + ":" +
					dt.substring(10, 12) + ":" +
					dt.substring(12, 14);
		} catch (Exception e) {
			System.out.println("Date Type mismatch!!\n" + e);
			return null;
		}
	}

	public static Connection getConnection() throws SQLException {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl11";
		Connection con = null;
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(url, "scott", "tiger");
		}catch(Exception e){
			System.out.println("Exception=["+ e +"]");
		}  
		return con;
	}
	public static void main(String args[]){
		System.out.println(Util.getCurrentDate(Util.getCurrentDate()));
	}

}
