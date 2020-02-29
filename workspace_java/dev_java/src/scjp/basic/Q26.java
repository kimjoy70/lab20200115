package scjp.basic;

public class Q26 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = 1;
		int j = i++;
		if((i == ++j) | (i++ == j)) {
			i += j;
		}
		System.out.println("i = " + i);
	}
}
