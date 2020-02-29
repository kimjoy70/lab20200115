package scjp.basic;

public class Q27 {

	public static void main(String[] args) {
		boolean a = false;
		boolean b = false;
		boolean c = ((a = true) | (b = true));
		System.out.println(a + "," + b + "," + c);
	}
}
