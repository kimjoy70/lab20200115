package basic.method;

public class IntHap {
	public static void main(String[] args) {
		EvenNumber en = new EvenNumber();
		int hap = 0;
		hap = en.intHap1(1, 10);
		//2 4 6 8 10
		System.out.println("hap ===> "+hap);
		OddNumber on = new OddNumber();
		hap = 0;
		hap = on.oddHap1(1, 10);
		//1 3 5 7 9 
		System.out.println("odd hap ===> " + hap);
	}
}
