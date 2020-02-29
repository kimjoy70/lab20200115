package com.address2019;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.*;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class ModifyAddrEty {
	public AddressVO modify(AddressVO vo) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String modifyStmt = " UPDATE mkaddrtb "
						+ " SET name = ?,"
						+ "     address = ?,"
						+ "     telephone = ?,"
						+ "     gender = ?,"
						+ "     relationship = ?,"
						+ "     birthday = ?,"
						+ "     comments = ?,"
						+ "     registedate = to_char(sysdate,'YYYYMMDD') "
						+ " WHERE id = ? ";
		int id = vo.getId();
		try{
			con = Util.getConnection();
			ps = con.prepareStatement(modifyStmt);
			ps.setString(1, vo.getName());
			ps.setString(2, vo.getAddress());
			ps.setString(3, vo.getTelephone());
			ps.setString(4, vo.getGender());
			ps.setString(5, vo.getRelationship());
			ps.setString(6, vo.getBirthday());
			ps.setString(7, vo.getComments());
			ps.setInt(8, vo.getId());

			if(ps.executeUpdate() < 1) {
				String msg = "ID: " + id + "번 수정에 실패했습니다.";
				System.out.println(msg);
				throw new SQLException(msg);
			} else {
				System.out.println("ID: " + id + "번이 수정되었습니다.");
			}
		}catch(SQLException e) {
			String msg = "ID: " + id + "번 수정에 실패했습니다.";
			System.out.println(msg + "\r\n" + e);
			throw new SQLException(msg + "\r\n" + e);
		}finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(con != null) con.close();
			}catch(SQLException e) {}
		}
		return new AddressVO();

	}
	public void updateAddress(AddressVO vo) throws Exception
	{
		SqlMapClient sqlMap = null;
	    String resource = "com/address2019/SqlMapConfig.xml";
	    Reader reader = null;
	    List someList = null;
		try {
			reader = Resources.getResourceAsReader (resource);
	        sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
	        reader.close();
	        sqlMap.update("updateAddress",vo);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}