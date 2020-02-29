package scjp.loop;

public class If54 {

	public void testIfA() {
		if(testIfB("True")) {
			System.out.println("True");
		} else {
			System.out.println("Not true");
		}
	}
	public Boolean testIfB(String str) {
		return Boolean.valueOf(str);
	}
	public static void main(String[] args) {
		If54 if54 = new If54();
		if54.testIfA();
	}

}
