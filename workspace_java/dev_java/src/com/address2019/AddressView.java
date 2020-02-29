package com.address2019;
import java.io.*;

public class AddressView {

	public static void main(String[] args) {
		//1-insert 2-delete 3-update 4-select 5-selectall
		int xi = 5;
		String name = "자바";
		String address = "서울시 강남구 역삼동";
		String telephone = "02-123-1234";
		String gender = "1";
		String relationship = "친구";
		String birthday = "20010101";
		String comments = "참 좋은 친구";
		String registerdate = "20011212121212";
		int id = 4;

		AddressVO vo = new AddressVO(name, address, telephone, gender,
						relationship, birthday, comments, registerdate, id);
		AddressVO returnVO = new AddressVO();

		try {
			if (xi == 1) {
				vo.setCommand("insert");
				AddressCtrl ctrl = new AddressCtrl(vo);
				ctrl.send(vo);
				System.out.println("\n## 입력이 정상적으로 수행되었습니다. ##");
			} if (xi == 2) {
				vo.setCommand("delete");
				AddressCtrl ctrl = new AddressCtrl(vo);
				ctrl.send(vo);
				System.out.println("\n## 삭제가 정상적으로 수행되었습니다. ##");
			} if (xi == 3) {
				vo.setCommand("update");
				AddressCtrl ctrl = new AddressCtrl(vo);
				ctrl.send(vo);
				System.out.println("\n## 수정이 정상적으로 수행되었습니다. ##");
			} if (xi == 4) {
				vo.setCommand("select");
				AddressCtrl ctrl = new AddressCtrl(vo);
				returnVO = ctrl.send(vo);

				System.out.println("이름\t: ["+ returnVO.getName() +"]");
				System.out.println("주소\t: ["+ returnVO.getAddress() +"]");
				System.out.println("전화번호: ["+ returnVO.getTelephone() +"]");
				System.out.println("성별\t: ["+ returnVO.getGender() +"]");
				System.out.println("관계\t: ["+ returnVO.getRelationship() +"]");
				System.out.println("생일\t: ["+ returnVO.getBirthday() +"]");
				System.out.println("비고\t: ["+ returnVO.getComments() +"]");
				System.out.println("수정일시: ["+ returnVO.getRegistedate() +"]");
				System.out.println("ID\t: ["+ returnVO.getId() +"]");

				System.out.println("\n## 조회가 정상적으로 수행되었습니다. ##");
			} if (xi == 5) {
				vo.setCommand("selectall");
				AddressCtrl ctrl = new AddressCtrl(vo);
				AddressVO[] rvos = ctrl.send();
				int i = rvos.length;
				System.out.println("\n전체건수: ["+ rvos.length +"]\n");
				for (int j = 0; j < i; j++) {
					System.out.println("이름\t: ["+ rvos[j].getName() +"]");
					System.out.println("주소\t: ["+ rvos[j].getAddress() +"]");
					System.out.println("전화번호: ["+ rvos[j].getTelephone() +"]");
					System.out.println("성별\t: ["+ rvos[j].getGender() +"]");
					System.out.println("관계\t: ["+ rvos[j].getRelationship() +"]");
					System.out.println("생일\t: ["+ rvos[j].getBirthday() +"]");
					System.out.println("비고\t: ["+ rvos[j].getComments() +"]");
					System.out.println("수정일시: ["+ rvos[j].getRegistedate() +"]");
					System.out.println("ID\t: ["+ rvos[j].getId() +"]\n");
				}

				System.out.println("\n## 리스트 조회가 정상적으로 수행되었습니다. ##");
			}
		} catch(Exception e) {
			System.out.println("에러가 발생했습니다.\n" + e.getMessage());
		}
	}

}