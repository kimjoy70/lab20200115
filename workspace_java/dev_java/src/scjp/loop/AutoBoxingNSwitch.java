package scjp.loop;

public class AutoBoxingNSwitch {
	public static void main(String[] args) {
		Integer i = new Integer(1) + new Integer(2);
		switch(i) {
//			case 3: System.out.println("three"); break;
			case 3: System.out.println("three");
			default: System.out.println("other"); break;
		}//end of switch
	}
}
