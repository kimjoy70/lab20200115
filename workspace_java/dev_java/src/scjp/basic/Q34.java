package scjp.basic;

public class Q34 {
	private static int a;
	public static void main(String[] args) {
		modify(a);
		System.out.println(a);//0
	}
	public static void modify(int a) {
		a++;
	}
}
