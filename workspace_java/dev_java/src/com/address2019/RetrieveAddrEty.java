package com.address2019;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;


public class RetrieveAddrEty {

	public AddressVO[] retrieve() throws SQLException {

		Connection con = null;
		Statement sm = null;
		ResultSet rs = null;
		AddressVO[] vos = null;
		AddressVO vo = null;
		Vector v = new Vector(1,1);
		String retrieveStmt = " SELECT name, address, telephone, gender, relationship, "
							+ "        birthday, comments, registedate, id "
							+ " FROM   mkaddrtb "
							+ " ORDER BY name";
		try {
			con = Util.getConnection();
			sm = con.createStatement();
			rs = sm.executeQuery(retrieveStmt);

			while (rs.next()) {
				vo = new AddressVO(rs.getString(1), rs.getString(2),
							rs.getString(3), rs.getString(4), rs.getString(5),
							rs.getString(6), rs.getString(7), rs.getString(8),
							rs.getInt(9));
				v.addElement(vo);
			}
			vos = new AddressVO[v.size()];
			v.copyInto(vos);

		} catch(SQLException e) {
			String msg = "전체 데이터 조회중 에러가 발생했습니다.";
			System.out.println(msg + "\r\n" + e);
			throw new SQLException(msg + "\r\n" + e);
		} finally {
			try {
				if(rs != null) rs.close();
				if(sm != null) sm.close();
				if(con != null) con.close();
			} catch(SQLException e) { }
		}

		return vos;
	}

	public List<AddressVO> retrieveAll() throws SQLException {
		SqlMapClient sqlMap = null;
		AddressVO[] vos = null;
		List<AddressVO> someList;
		try {
		    String resource = "com/address2019/SqlMapConfig.xml";
		    Reader reader = null;			
			reader = Resources.getResourceAsReader (resource);
	        sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
	        reader.close();
	        someList = (List)sqlMap.queryForList("retrieveAll");
		} catch(IOException e) {
			String msg = "전체 데이터 조회중 에러가 발생했습니다.";
			System.out.println(msg + "\r\n" + e);
			throw new SQLException(msg + "\r\n" + e);
		}
		return someList;
	}	
	public List<AddressVO> myBatisRetrieveAll() throws SQLException {
		SqlSessionFactory sqlMapper = null;
		SqlSession sqlSes = null;
		AddressVO[] vos = null;
		List<AddressVO> someList;
		try {
			String resource = "com/address2019/MapperConfig.xml";
			Reader reader = null;			
			reader = Resources.getResourceAsReader (resource);
			sqlMapper = new SqlSessionFactoryBuilder().build(reader);
			sqlSes = sqlMapper.openSession();
			reader.close();
			someList = (List)sqlSes.selectList("getAddressList");
		} catch(IOException e) {
			String msg = "전체 데이터 조회중 에러가 발생했습니다.";
			System.out.println(msg + "\r\n" + e);
			throw new SQLException(msg + "\r\n" + e);
		}
		return someList;
	}	

	public AddressVO retrieve(AddressVO vo) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String retrieveStmt = " SELECT name, address, telephone, gender, "
							+ "        relationship, birthday, comments, "
							+ "        registedate, id "
							+ " FROM   mkaddrtb "
							+ " WHERE  id = ? ";
		int id = vo.getId();

		try {
			con = Util.getConnection();

			ps = con.prepareStatement(retrieveStmt);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				vo = new AddressVO(rs.getString(1), rs.getString(2),
									rs.getString(3), rs.getString(4),
									rs.getString(5), rs.getString(6),
									rs.getString(7), rs.getString(8),
									rs.getInt(9));
			} else {
				throw new SQLException("ID: " + id + "번에 해당하는 데이터가 없습니다.");
			}
		} catch(SQLException e) {
			String msg = "ID: " + id + "번 조회중 에러가 발생했습니다.";
			System.out.println(msg);
			throw new SQLException(msg + "\r\n" + e);
		} finally {
			try {
				if(rs  != null) rs.close();
				if(ps  != null) ps.close();
				if(con != null) con.close();
			} catch(SQLException e) {}
		}
		return vo;
	}

}