package scjp.basic;

public class Q31 {

	public static void main(String[] args) {
		int i = 0, j = 1;
		if((i++ == 1) && (j++ == 2)) {
			i = 42;
		}
		System.out.println("i = " + i + ", j = " + j);
	}
}
//i = 1, j = 1