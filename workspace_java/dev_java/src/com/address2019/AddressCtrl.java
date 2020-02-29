package com.address2019;
import java.io.*;
import java.util.*;

public class AddressCtrl {

	private AddressVO returnVO = new AddressVO();
	private AddressVO inVO = new AddressVO();

	private static String _DEL = "delete";
	private static String _INS = "insert";
	private static String _MOD = "update";
	private static String _SEL = "select";
	private static String _ALL = "selectall";

	public AddressCtrl(AddressVO vo) {
		this.inVO = vo;
	}

	public AddressVO send(AddressVO vo) throws Exception {
		String command = inVO.getCommand();

		if (command.equals(_DEL)) {
			DeleteAddrEty delEty = new DeleteAddrEty();
			returnVO = delEty.delete(vo);
			delEty.deleteAddress(vo);
		} else if (command.equals(_INS)) {
			RegisterAddrEty insEty = new RegisterAddrEty();
//			returnVO = insEty.register(vo);
			insEty.insertAddress(vo);
		} else if (command.equals(_MOD)) {
			ModifyAddrEty modEty = new ModifyAddrEty();
			returnVO = modEty.modify(vo);
			modEty.updateAddress(vo);
		} else if (command.equals(_SEL)) {
			RetrieveAddrEty selEty = new RetrieveAddrEty();
			returnVO = selEty.retrieve(vo);
		} else
			throw new Exception("잘못된 Command명(" + command + ")입니다.");

		return returnVO;
	}

	public AddressVO[] send() throws Exception {
		String command = inVO.getCommand();

		AddressVO[] returnVOs = null;
		if (command.equals(_ALL)) {
			RetrieveAddrEty retEty = new RetrieveAddrEty();
			returnVOs = retEty.retrieve();
		} else
			throw new Exception("잘못된 List 조회 Command입니다.");

		return returnVOs;
	}
	/********************************************************************************************
	 * 
	 * IBATIS 적용하기 
	 * 
	 *******************************************************************************************/
	public List<AddressVO> isend(AddressVO vo) throws Exception {
		List<AddressVO> list = null;
		String command = inVO.getCommand();

		if (command.equals(_DEL)) {
			DeleteAddrEty delEty = new DeleteAddrEty();
			returnVO = delEty.delete(vo);
		} else if (command.equals(_INS)) {
			RegisterAddrEty insEty = new RegisterAddrEty();
			returnVO = insEty.register(vo);
		} else if (command.equals(_MOD)) {
			ModifyAddrEty modEty = new ModifyAddrEty();
			returnVO = modEty.modify(vo);
		} else if (command.equals(_SEL)) {
			RetrieveAddrEty selEty = new RetrieveAddrEty();
			returnVO = selEty.retrieve(vo);
		} else
			throw new Exception("잘못된 Command명(" + command + ")입니다.");

		return list;
	}	
	public List<AddressVO> sendAll() throws Exception {
		String command = inVO.getCommand();
		List<AddressVO> selectAll = null;
		if (command.equals(_ALL)) {
			RetrieveAddrEty retEty = new RetrieveAddrEty();
			selectAll = retEty.retrieveAll();
			selectAll = retEty.myBatisRetrieveAll();
		} else
			throw new Exception("잘못된 List 조회 Command입니다.");

		return selectAll;
	}	
}
	