package scjp.basic;

public class Q24 {

	public static void main(String[] args) {
		String s = "abcde";
		Object ob = (Object)s;
		if(ob.equals(s)) {
			System.out.println("AAAA");
		} else {
			System.out.println("BBBB");
		}
		if(s.equals(ob)) {
			System.out.println("CCCC");
		} else {
			System.out.println("DDDD");
		}
	}
}
