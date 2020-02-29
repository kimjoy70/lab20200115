package scjp.method;

public class A39 {
//	private int counter = 0;
	private static int counter = 0;
	public static int getInstanceCount() {
		return counter;
	}
	public A39() {
		counter++;
	}
}
