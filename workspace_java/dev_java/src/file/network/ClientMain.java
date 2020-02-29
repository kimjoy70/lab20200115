package file.network;

import java.io.*;
import java.net.*;
import java.util.*;

public class ClientMain {
	final static String SERVER_IP="127.0.0.1";
	final static int PORT=5000;
	//List<File> fileList;
	File [] fileArr;
	byte [] bytes=new byte[1024];
	int len;
	int count;
	int leftLen;
	InputStream ips;
	OutputStream ops;
	PrintStream ps;
	DataOutputStream dos; 
	public ClientMain() throws UnknownHostException, IOException {
		Socket socket=new Socket(SERVER_IP, PORT);
		ips=socket.getInputStream();
		ops=socket.getOutputStream();
		ps=new PrintStream(ops);
		dos=new DataOutputStream(socket.getOutputStream());
		System.out.println("CLIENT");
		
		BufferedOutputStream bos=new BufferedOutputStream(socket.getOutputStream());
		
		//전송 순서
		//int 배열크기
		//int 파일 이름 길이
		//string -> bytes 파일 이름
		//long 파일 크기
		//bytes 파일 데이터
		
		File deskTop=new File("C:\\Users\\Public\\Pictures\\Sample Pictures");
		
		
		
		System.out.println("DeskTop");
		File [] fileArr=deskTop.listFiles();
		
		dos.writeInt(fileArr.length);
		//여기서 Scanner로 끊어주기.
		//클라이언트가 먼저 scanner 입력하면 안 돼
		Scanner s=new Scanner(System.in);
		for(File file:fileArr) {
			if(file.isFile()) {
				System.out.print(file.getName()+"\tsize  "+file.length()+"\t");
				ops.write((byte)( file.getName().length()));
				//dos.writeInt(file.getName().length());	
				ps.print(file.getName());
				dos.writeLong(file.length());
				BufferedInputStream bis=new BufferedInputStream(new FileInputStream(file));
				
				len=0;
				long size=file.length();
				
				count=(int)(size/1024);
				leftLen=(int)(size-(count*1024));
				
				for(int j=0; j<count; j++) {
					bis.read(bytes);
					bos.write(bytes);

				}
				bis.read(bytes, 0, leftLen);
				bos.write(bytes, 0, leftLen);
				
				
				bos.flush();	//이거 꼭 해줘야 하더라.
				
				
				try {
					Thread.sleep(50);
					//이거 안 해주면 데이터가 섞이더라.. 섞일 부분은 없는ㄷ ㅔㅜㅜㅜ
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			System.out.println("Finished\n");
		}
	
		
	}
	public static void main(String[] args) throws IOException {
		ClientMain main=new ClientMain();
		
	}
}
