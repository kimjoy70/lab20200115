package com.network2;

import java.util.StringTokenizer;

public class StringTokenizerTest {

	public static void main(String[] args) {
		String msg = 100+"|"+"���ʺ�"+"|"+"���� ���͵��ұ�?";
		StringTokenizer st = new StringTokenizer(msg,"|");
		String protocol = st.nextToken();
		String chatName = st.nextToken();
		String message  = st.nextToken();
		System.out.println(protocol);
		System.out.println(chatName);
		System.out.println(message);
		System.out.println("�� ������?"+st.nextToken());
	}

}
