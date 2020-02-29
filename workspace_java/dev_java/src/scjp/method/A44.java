package scjp.method;

public class A44 {
	public String doit(int x, int y) {
		return "a";
	}
	
	public String doit(int... vals) {  //비정형인자
		System.out.println("vals : "+vals);
		System.out.println("vals : "+vals[0]);
		return "b";
	}
	public static void main(String[] args) {
		A44 a=new A44();
		//System.out.println(a.doit(4, 5));
		System.out.println(a.doit(4, 5, 6));		
		System.out.println(a.doit(4));		
	}

}
