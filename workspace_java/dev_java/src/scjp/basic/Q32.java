package scjp.basic;

public class Q32 {
	int x;
	boolean check() {
		System.out.println("check호출 "+x);
		x++;
		return true;
	}
	void zzz() {
		x = 0;
		//뒤에 있는 조건은 안따짐. || 두개니까
		if((check() | check()) || check()) {
			x++;
		}
		System.out.println("x = " + x);
	 }
	 public static void main(String[] args) {
		 (new Q32()).zzz();
	 }
}
//x = 3
