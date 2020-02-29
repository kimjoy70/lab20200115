package com.network2;

import java.util.Vector;
// 110|roomTitle|Vector
//StringTokener st = new StringTokener("110|roomTitle|Vector","|");
//String s = st.nextToken();
public class Room {
	Vector<String> users = new Vector<String>();
	int roomno = 0;
	String roomTitle = null;
	int inwon = 0;
	public Room() {}
	public Room(int roomno, String roomTitle, int inwon, Vector<String> users)
	{
		this.roomTitle = roomTitle;
		this.roomno = roomno;
		this.inwon = inwon;
		this.users = users;
	}
	public Vector<String> getUsers() {
		return users;
	}
	public void setUsers(Vector<String> users) {
		this.users = users;
	}
	public int getRoomno() {
		return roomno;
	}
	public void setRoomno(int roomno) {
		this.roomno = roomno;
	}
	public String getRoomTitle() {
		return roomTitle;
	}
	public void setRoomTitle(String roomTitle) {
		this.roomTitle = roomTitle;
	}
	public int getInwon() {
		return inwon;
	}
	public void setInwon(int inwon) {
		this.inwon = inwon;
	}
	
	
}
