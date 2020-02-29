package com.network2;

import java.util.StringTokenizer;

public class StringTokenizerTest {

	public static void main(String[] args) {
		String msg = 100+"|"+"나초보"+"|"+"오늘 스터디할까?";
		StringTokenizer st = new StringTokenizer(msg,"|");
		String protocol = st.nextToken();
		String chatName = st.nextToken();
		String message  = st.nextToken();
		System.out.println(protocol);
		System.out.println(chatName);
		System.out.println(message);
		System.out.println("더 있을까?"+st.nextToken());
	}

}
