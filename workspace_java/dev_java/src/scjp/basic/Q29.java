package scjp.basic;

public class Q29 {

	public static void main(String[] args) {
		int i = 0, j = 1;
		if((i++ == 0) || (j++ == 2)) {
			i = 42;
		}
		System.out.println("i = " + i + ", j = " + j);
	}
}

//i = 42, j = 1