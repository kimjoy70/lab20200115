package scjp.loop;

public class Switch52 {
	public enum Dogs {collie, harrier, shepherd};
	public static void main(String [] args) {
		Dogs myDog = Dogs.shepherd;
		switch (myDog) {
			case collie:
			System.out.print("collie ");
			default:  //case default: 가 아니고 default: 이다
			System.out.print("retriever ");
			case harrier:
			System.out.print("harrier ");
		}
	}
}
