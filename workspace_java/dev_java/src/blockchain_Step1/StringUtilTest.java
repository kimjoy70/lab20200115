package blockchain_Step1;

public class StringUtilTest {

	public static void main(String[] args) {
		String mem_id = "test";
		String sha256_id = null;
		sha256_id = StringUtil.applySha256(mem_id);
		System.out.println("sha256적용전 값 ==> "+mem_id);
		System.out.println("sha256적용값 ==> "+sha256_id);
		String mem_name = "김유신";
		String sha256_name = null;
		sha256_name = StringUtil.applySha256(mem_name);
		System.out.println("sha256적용전 값 ==> "+mem_name);
		System.out.println("sha256적용값 ==> "+sha256_name);
	}

}
