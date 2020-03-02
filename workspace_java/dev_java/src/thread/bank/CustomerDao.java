package thread.bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.util.DBConnectionMgr;

public class CustomerDao {
	DBConnectionMgr dbMgr = DBConnectionMgr.getInstance();
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	CustomerBank cBank = null;
	DaeLimApt dApt = null;
	SKTelecom skt = null;
	ServerBank sBank = null;
	public CustomerDao(CustomerBank cBank) {
		this.cBank = cBank;
	}
	public CustomerDao(DaeLimApt dApt) {
		this.dApt = dApt;
	}
	public CustomerDao(SKTelecom skt) {
		this.skt = skt;
	}
	public CustomerDao(ServerBank sBank) {
		this.sBank = sBank;
	}
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
	public int dealInsert(Map<String,Object> pMap) {
		int result = 0;
		StringBuilder sql = new StringBuilder();
		try {
			sql.append("INSERT INTO bankaccount2020(ba_no,daccountnumber,deposit                ");
            sql.append("        ,withdraw,dealdate,withdrawmsg                                  ");
            sql.append("        ,depositmsg,mem_id,waccountnumber)                              ");
            sql.append(" VALUES(seq_bank2020.nextval,?,?,?,TO_CHAR(sysdate,'YYYY-MM-DD'),?,?,?,?)");			
			con = dbMgr.getConnection();
			pstmt = con.prepareStatement(sql.toString());
			//pstmt.setString(1, mem_id);
			int i = 0;
			pstmt.setString(++i, pMap.get("daccountnumber").toString());
			pstmt.setString(++i, pMap.get("transfer").toString());//입금액
			pstmt.setString(++i, pMap.get("transfer").toString());//출금액
			pstmt.setString(++i, pMap.get("withdrawmsg").toString());
			pstmt.setString(++i, pMap.get("depositmsg").toString());
			pstmt.setString(++i, pMap.get("mem_id").toString());
			pstmt.setString(++i, pMap.get("waccountnumber").toString());
			result = pstmt.executeUpdate();
			JOptionPane.showMessageDialog(cBank, "result:"+result);
			if(result == 1) {
				//입금계좌번호에서 이체금액 증가시키기
				pstmt = null;
				sql = null;
				sql = new StringBuilder();
				sql.append("update member2020 set balance=balance+?");
				sql.append(" where accountnumber = ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, Integer.parseInt(pMap.get("transfer").toString()));
				pstmt.setString(2, pMap.get("daccountnumber").toString());
				int dr = pstmt.executeUpdate();
				//출금계좌번호에서 이체금액 차감시키기
				pstmt = null;
				sql = null;
				sql = new StringBuilder();
				sql.append("update member2020 set balance=balance-?");
				sql.append(" where accountnumber = ?");
				pstmt = con.prepareStatement(sql.toString());
				pstmt.setInt(1, Integer.parseInt(pMap.get("transfer").toString()));
				pstmt.setString(2, pMap.get("waccountnumber").toString());
				int wr = pstmt.executeUpdate();
				JOptionPane.showMessageDialog(cBank, "dr:"+dr+" , wr:"+wr);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result;
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
