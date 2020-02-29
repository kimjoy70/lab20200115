package scjp.basic;

public class Q35 {
	public static void main(String[] args) {
		String s = new String("Hello");
		modify(s);
		System.out.println(s);
	}
	public static void modify(String s) {
		s += "world!";
	}
}
