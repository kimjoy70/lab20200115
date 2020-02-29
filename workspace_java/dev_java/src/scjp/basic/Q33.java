package scjp.basic;

public class Q33 {
	public static void main(String[] args) {
		int[] a = new int[1];
		modify(a);
		System.out.println(a[0]);//1
	}
	public static void modify(int[] a) {
		a[0]++;
	}
}
