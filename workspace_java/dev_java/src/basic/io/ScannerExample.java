package basic.io;

import java.util.Scanner;
public class ScannerExample {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String inputData = null;
		while(true) {
			inputData = scan.nextLine();
			System.out.println("입력된 문자열 : \"" + inputData + "\"");
			if(inputData.equals("q")) {
				break;
			}
		}/////end of while
		System.out.println("종료");
	}
}
/*
a
입력된 문자열 : "a"
안녕
입력된 문자열 : "안녕"
q
입력된 문자열 : "q"
종료
*/