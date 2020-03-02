package thread.bank;

public class Protocol {
	public static final int WAIT 	    = 100; 
	public static final int ROOM_CREATE = 110;
	public static final int ROOM_LIST	= 120;
	public static final int ROOM_IN 	= 130; 
	public static final int ROOM_NO 	= 131; 
	public static final int ROOM_INLIST	= 140; 
	public static final int ROOM_OUT 	= 190; 
	public static final int MESSAGE 	= 200; 
	public static final int WHISHER 	= 201; 
	public static final int CHANGE   	= 300; 
	public static final int DEPOSIT   	= 400;//입금 
	public static final int WITHDRAW    = 401;//출금 
	public static final int BALCHECK    = 402;//잔액확인 
	public static final String seperator= "#"; 
}
