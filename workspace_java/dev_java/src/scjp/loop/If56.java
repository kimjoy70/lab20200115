package scjp.loop;

public class If56 {
	public static void main(String[] args) {
		String str = "null";
		if (str == null) {
			System.out.println("null");
		} 
		else if (str.length() == 0) {  //else if
			System.out.println("zero");
		} 
		else {
			System.out.println("some");
		}
	}
}
