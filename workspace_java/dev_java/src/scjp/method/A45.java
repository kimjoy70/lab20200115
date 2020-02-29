package scjp.method;

public class A45 {
	public void doit() {
	}
	/*
	public String doit() {  //위의 public void doit()의 인자의 개수와 데이터형이 같으면 오류
		return "a";
	}
	*/
	public String doit2() {  //위의 public void doit()의 인자의 개수와 데이터형이 같으면 오류
		return "a";
	}
	public double doit(int x) {
		return 1.0;
	}
	public static void main(String[] args) {
		A45 a45 = new A45();
		a45.doit(5);
		System.out.println(a45.doit(5));
	}

}
