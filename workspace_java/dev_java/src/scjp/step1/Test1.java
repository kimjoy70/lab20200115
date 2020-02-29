package scjp.step1;

public class Test1 {

	public static void main(String[] args) {
		int x =5;
		boolean b1 = true;
		boolean b2 = false;
		if((x==4) && !b2)  //&&는 둘다 true 일때  ture다 : x==4는 거짓 그러므로 false
			System.out.print("1 ");  //참일 때 실행
		System.out.print("2 ");  //거짓일 때 실행 : 17번 행의 결과가 거짓이기 때문에 실행됨
		if ((b2 = true) && b1)  //b2의 값은 false인데 true값으로 대입하여 true, b1도 true
			System.out.print("3 ");  //참일 때 실행 : 20번 행의 결과가 참이므로 실행됨
	}
}

