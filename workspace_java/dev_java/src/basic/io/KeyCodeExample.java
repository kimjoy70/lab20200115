package basic.io;

public class KeyCodeExample {
/*
\r 은 캐리지 리턴을
\n 은 라인 피드를 나타냅니다.
둘은 의미가 약간 다른데,
캐리지 리턴은 커서의 위치를 현재 커서가 위치한 줄의 맨 처음으로 보내는 기능을 하고
라인 피드는 커서를 다음줄로 옮기는 역할을 합니다. 따라서 라인 피드는 커서가 둘째줄
셋째칸에 있었다면 셋째줄 셋째칸으로 옮기는 것이죠.
그래서 옛날에 캐리지 리턴과 라인 피드를 쓰면 보통 우리가 '엔터'키를 누른 듯한 효과를
냅니다.
 */
	public static void main(String[] args) throws Exception {
		int keyCode;
		keyCode = System.in.read();
		System.out.println("keyCode: "+ keyCode);
		keyCode = System.in.read();
		System.out.println("keyCode: "+ keyCode);
		keyCode = System.in.read();
		System.out.println("keyCode: "+ keyCode);
	}
}
/*
a
keyCode: 97
keyCode: 13
keyCode: 10
*/