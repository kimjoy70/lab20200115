package collection.step1;
import java.util.ArrayList;
import java.util.List;
public class ListTest1 {
	public void setList(List<String> nameList) {
		nameList.add("이순신");
		nameList.add("강감찬");
		nameList.add("김유신");
	}
	public void listPrint(List<String> nameList) {
		for(int i=0;i<nameList.size();i++) {
			System.out.println(nameList.get(i));
		}
	}
	public static void main(String[] args) {
		List<String> nameList = new ArrayList<>();
		ListTest1 lt1 = new ListTest1();
		lt1.setList(nameList);
		lt1.listPrint(nameList);
	}
}
