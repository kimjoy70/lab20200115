package method.temparature;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import com.util.DBConnectionMgr;

public class SeoulTempDAO {
	DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public String[] getYear() {
		String years[] = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT distinct(ta_year) ta_year");
		sb.append("  FROM (SELECT TO_CHAR(TO_DATE(ta_date),'YYYY') ta_year FROM temperature2019");
		sb.append(" WHERE TO_CHAR(TO_DATE(ta_date),'YYYY') > TO_CHAR(sysdate,'YYYY')-11)");
		sb.append(" ORDER BY ta_year desc");
		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			Vector<String> v= new Vector<>();
			while(rs.next()) {
				String ta_year = rs.getString("ta_year");
				v.add(ta_year);
			}
			years = new String[v.size()];
			v.copyInto(years);
			System.out.println("size : "+v.size());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return years;
	}
	public String[] getMonth(String year) {
		String months[] = null;
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT distinct(to_char(to_date(ta_date),'MM')) ta_mm");
		sb.append("  FROM temperature2019");
		sb.append(" WHERE TO_CHAR(TO_DATE(ta_date),'YYYY')=?");
		sb.append(" ORDER BY to_char(to_date(ta_date),'MM') asc");
		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			pstmt.setString(1, year);
			rs = pstmt.executeQuery();
			Vector<String> v= new Vector<>();
			while(rs.next()) {
				String ta_mm = rs.getString("ta_mm");
				v.add(ta_mm);
			}
			months = new String[v.size()];
			v.copyInto(months);
			System.out.println("size : "+v.size());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return months;
	}	
	public List<Map<String,Object>> getTempList(SeoulTempVO stVO){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ta_date, ta_high, ta_low, ta_avgt FROM temperature2019");
		if(stVO.getTa_date()!=null) {
				sb.append(" WHERE ta_date =?");
		}
		else {
			sb.append(" WHERE ta_date LIKE ?");
				
		}
		try {
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sb.toString());
			String imsi = "";
			if(stVO.getTa_date()!=null) {			
				pstmt.setString(1, stVO.getTa_date());
			}else {
				imsi = stVO.getnYear()+"/"+stVO.getnMonth()+"%";
				pstmt.setString(1, imsi);
			}
			System.out.println("? ==>"+imsi);
			rs = pstmt.executeQuery();
			Map<String,Object> rMap = null;
			while(rs.next()) {
				rMap = new HashMap<>();
				rMap.put("ta_high",rs.getDouble("ta_high"));
				rMap.put("ta_low",rs.getDouble("ta_low"));
				rMap.put("ta_date",rs.getString("ta_date"));
				list.add(rMap);
			}
			System.out.println("[[query]]"+sb.toString());
			System.out.println("size : "+list.size());
		}catch(SQLException se) {
			System.out.println("[[query]]"+sb.toString());
		}catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
