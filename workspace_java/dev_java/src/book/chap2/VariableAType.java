package book.chap2;

import java.util.Scanner;

/*
 * 전역변수와 지역변수의 차이점에 대해서 말할 수 있다.
 * 전역변수와 지역변수의 선언 위치에 대해 설명할 수 있다.
 * 반드시 전역변수로 선언해야 하는 경우는 언제일까?
 * 지역변수는 어떤 경우에 사용하면 좋을까?
 * 
 * 다음 문제를 통해 위 학습목표를 달성해 봅시다.
 */
/*************************************************************************************************
1. 달의 중력은 지구 중력의 17%에 불과합니다. 지구에서 몸무게가 100kg인 사람은 달에 가면 17kg밖에 안됩니다.
당신의 몸무게는 달에서 얼마입니까?
몸무게 N은 실수이고 키보드로부터 입력받습니다.

출력)  지구몸무게  : 100
          달 몸무게 : 17kg
*************************************************************************************************/
public class VariableAType {
	//달의 몸무게를 계산하는 메소드를 선언하고 구현하시오.
	double moonWeight(double ewg) {
		double mwg = (ewg*17)/100;
		return mwg;
	}
	public static void main(String[] args) {
		System.out.println("지구의 몸무게를 입력하세요.");
		Scanner scan = new Scanner(System.in);
		double ewg = scan.nextDouble();
		VariableAType vat = new VariableAType();
		double mwg = 0.0;
		mwg = vat.moonWeight(ewg);
		System.out.println("달의 몸무게는 ===> "+mwg);
	}
}
