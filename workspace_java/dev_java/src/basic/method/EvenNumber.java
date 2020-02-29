package basic.method;

public class EvenNumber {
	public int intHap1(int start, int end) {
		int hap = 0;
		for(int i=start;i<=end;i++) {
			if(i%2==0) {
				hap = hap+i;
			}
		}
		return hap;
	}
}
