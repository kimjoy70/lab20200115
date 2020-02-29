package scjp.loop;

public class For63 {
	public static void main(String[] args) {
		int i;
		for (i=0;i<=10;i++){
			if( i>6) break;
		}
		System.out.println(i);  //오류
	}
}
