package com.address2019;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.*;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class RegisterAddrEty {

	public AddressVO register(AddressVO vo) throws Exception {
		Connection con = null;
		Statement sm = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int id = 0;
		String registerStmt = " INSERT INTO mkaddrtb "
						+ " (name, address, telephone, gender, relationship, "
						+ "  birthday, comments, registedate, id) "
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String checkIDStmt = "SELECT COUNT(id) FROM mkaddrtb";
		String selectIDStmt = "SELECT MAX(id) + 1 FROM mkaddrtb";

		try {
			con = Util.getConnection();
			sm = con.createStatement();

			rs = sm.executeQuery(checkIDStmt);
			rs.next();
			if(rs.getInt(1) > 0) {
				rs = sm.executeQuery(selectIDStmt);
				if(rs.next()) id = rs.getInt(1);
			} else {
				id = 1;
			}

			ps = con.prepareStatement(registerStmt);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getAddress());
			ps.setString(3, vo.getTelephone());
			ps.setString(4, vo.getGender());
			ps.setString(5, vo.getRelationship());
			ps.setString(6, vo.getBirthday());
			ps.setString(7, vo.getComments());
			ps.setString(8, vo.getRegistedate());
			ps.setInt(9, id);
			if(ps.executeUpdate() < 1) {
				String msg = "데이터 입력에 실패했습니다.";
				System.out.println(msg);
				throw new SQLException(msg);
			} else {
				System.out.println("데이터 입력에 성공했습니다.");
			}
		} catch(SQLException e) {
			String msg = "데이터 입력에 실패했습니다.";
			System.out.println(msg + "\r\n" + e);
			throw new SQLException(msg + "\r\n" + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(sm != null) sm.close();
				if(ps != null) ps.close();
				if(con != null) con.close();
			} catch(SQLException e) {}
		}

		return new AddressVO();
	}

	
	public void insertAddress(AddressVO vo) throws Exception {
		SqlMapClient sqlMap = null;
	    String resource = "com/address2019/SqlMapConfig.xml";
	    Reader reader = null;
	    List someList = null;
		try {
			reader = Resources.getResourceAsReader (resource);
	        sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
	        reader.close();
	        sqlMap.insert("insertAddress",vo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}








