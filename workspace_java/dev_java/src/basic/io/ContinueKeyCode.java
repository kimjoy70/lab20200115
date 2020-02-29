package basic.io;

public class ContinueKeyCode {
	public static void main(String[] args) throws Exception{
		int keyCode;
		while(true) {
			keyCode = System.in.read();
			System.out.println("keyCode: "+keyCode);
			if(keyCode == 113) {
				break;
			}
		}///end of while
		System.out.println("종료");
	}///////end of main
}
/*
a
keyCode: 97
keyCode: 13
keyCode: 10
hi
keyCode: 104
keyCode: 105
keyCode: 13
keyCode: 10
q
keyCode: 113
종료
*/