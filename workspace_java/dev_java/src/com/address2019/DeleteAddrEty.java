package com.address2019;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.*;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class DeleteAddrEty {

	public AddressVO delete(AddressVO vo) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String deleteStmt = " DELETE FROM mkaddrtb WHERE id =? ";
		int id = vo.getId();

		try {
			con = Util.getConnection();
			ps = con.prepareStatement(deleteStmt);
			ps.setInt(1, id);

			if(ps.executeUpdate() < 1)
				throw new SQLException("ID: " + id + "번 삭제에 실패했습니다.");
			else
				System.out.println("ID: " + id + "번이 삭제되었습니다.");
		} catch(SQLException e) {
			String msg = "ID: " + id + "번 삭제에 실패했습니다.";
			System.out.println(msg + "\r\n" + e);
			throw new SQLException(msg + "\r\n" + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(ps != null) ps.close();
				if(con != null) con.close();
			}catch(SQLException e) {}
		}
		return new AddressVO();
	}
	public void deleteAddress(AddressVO vo) throws SQLException{
		SqlMapClient sqlMap = null;
	    String resource = "com/address2019/SqlMapConfig.xml";
	    Reader reader = null;
		try {
			reader = Resources.getResourceAsReader (resource);
	        sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
	        reader.close();
	        sqlMap.update("deleteAddress",vo);
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
}