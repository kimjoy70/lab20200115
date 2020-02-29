package collection.step1;
import java.util.ArrayList;
import java.util.List;
public class ListTest2 {
	List<String> nameList = new ArrayList<>();
	public void setList() {
		nameList.add("이순신");
		nameList.add("강감찬");
		nameList.add("김유신");
	}
	public void listPrint() {
		for(int i=0;i<nameList.size();i++) {
			System.out.println(nameList.get(i));
		}
	}	
	public static void main(String[] args) {
		ListTest2 lt2 = new ListTest2();
		lt2.setList();
		lt2.listPrint();
	}
}
