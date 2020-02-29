package scjp.method;

public class Foo36 {
	static void alpha() { 
		/* more code here */ 
		System.out.println("alpha 호출 성공");
		//beta();
		
	}
	void beta() {
		/* more code here */ 
		System.out.println("beta 호출 성공");
		alpha();
	}
	public static void main(String[] args) {
		//Foo36.alpha();
		Foo36 f36 = new Foo36();
		f36.beta();
	}
}
