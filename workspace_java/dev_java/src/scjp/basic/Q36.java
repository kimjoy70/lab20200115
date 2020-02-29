package scjp.basic;

public class Q36 {
	public static void add3(Integer i) {
		int val = i.intValue();
		val += 4;
		i = new Integer(val);
	}
	public static Integer add4(Integer i) {
		int val = i.intValue();
		val += 4;
		i = new Integer(val);
		return i;
	}
	
	public static void main(String args[]) {
		Integer i = new Integer(0);
		add3(i);
		System.out.println(i.intValue());
		System.out.println("===============================");
		i = add4(i);
		System.out.println(i.intValue());
		
	}
}
