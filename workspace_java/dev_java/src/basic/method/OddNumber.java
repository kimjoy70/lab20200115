package basic.method;

public class OddNumber {
	public int oddHap1(int start, int end) {
		int hap = 0;
		for(int i=start;i<=end;i++) {
			if(i%2==1) {
				hap = hap+i;
			}
		}
		return hap;
	}
}
