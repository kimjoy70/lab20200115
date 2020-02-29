package book.chap2;

import java.util.Scanner;

public class KeyboardInput {

	public static void main(String[] args) {
		System.out.println("지구의 몸무게를 입력하세요.");
		Scanner scan = new Scanner(System.in);
		int earth = scan.nextInt();
		System.out.println("입력한 값은 ===>"+earth);
		System.out.println("지구의 몸무게를 입력하세요 두 번째.");
		double earth2 = scan.nextDouble();
		System.out.println("입력한 값2 은 ===>"+earth2);
	}

}
