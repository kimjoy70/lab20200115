package thread.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.util.DBConnectionMgr;

public class CustomerDao {
	DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	public Map<String,Object> login(String mem_id){
		Map<String,Object> rMap = null;
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("SELECT accountnumber,balance,mem_name FROM member2020");
			sql.append(" WHERE mem_id=?");
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				rMap = new HashMap<>();
				rMap.put("accountnumber", rs.getString("accountnumber"));
				rMap.put("balance", rs.getString("balance"));
				rMap.put("mem_name", rs.getString("mem_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rMap;
	}
	public List<Map<String,Object>> getBankAccount(String mem_id){
		List<Map<String,Object>> baList = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("SELECT accountnumber,balance,mem_name, mem_id FROM member2020");
			//sql.append(" WHERE mem_id=?");
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			//pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			Map<String,Object> rMap = null;
			while(rs.next()) {
				rMap = new HashMap<>();
				rMap.put("accountnumber", rs.getString("accountnumber"));
				rMap.put("balance", rs.getString("balance"));
				rMap.put("mem_name", rs.getString("mem_name"));
				rMap.put("mem_id", rs.getString("mem_id"));
				baList.add(rMap);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return baList;
	}
}
