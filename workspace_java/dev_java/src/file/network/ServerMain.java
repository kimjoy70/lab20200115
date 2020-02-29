package file.network;

import java.io.*;
import java.net.*;
import java.util.*;


public class ServerMain {
	final static String SERVER_IP="127.0.0.1";
	final static int PORT=5000;
	//List<File> fileList;
	File [] fileArr;
	byte [] bytes=new byte[1024];
	int len;
	int nameLen;
	int arrlen;
	int count;
	int leftLen;
	File path;
	InputStream ips;
	OutputStream ops;
	PrintStream ps;
	DataOutputStream dos;
	DataInputStream dis;
	public ServerMain() throws UnknownHostException, IOException {
		ServerSocket serverSocket=new ServerSocket(PORT);
		Socket socket=serverSocket.accept();
		ips=socket.getInputStream();
		ops=socket.getOutputStream();
		dis=new DataInputStream(socket.getInputStream());
		System.out.println("ServerOpen");
		BufferedOutputStream bos;
		BufferedInputStream bis=new BufferedInputStream(socket.getInputStream());
		path = new File("");
		System.out.println(path.getAbsolutePath()+"- Data Saving Directory");
		arrlen=dis.readInt();
		System.out.println("FileArray Length  "+arrlen);
		
		
		//
		//클라이언트가 먼저 데이터 보내면 안 돼
		for(int i=0; i<arrlen; i++) {
			
			nameLen=ips.read();
			//nameLen=dis.readInt();

			ips.read(bytes,0,nameLen);
			String name=new String(bytes, 0, nameLen);
			long size=dis.readLong();
			System.out.println(i+"th\t"+name+"\t"+"name lenth  "+nameLen);
			//왜 여기서 파일 아웃풋 스트림을 열면 망하는 것인가.
			
			bos=new BufferedOutputStream(new FileOutputStream(path.getAbsolutePath()+"\\"+name));
			
			count=(int)(size/1024);
			leftLen=(int)(size-(count*1024));
			
			
			
			for(int j=0; j<count; j++) {
				bis.read(bytes);
				bos.write(bytes);
			}
			bis.read(bytes,0,leftLen);
			bos.write(bytes,0,leftLen);	
			bos.flush();
			bos.close();
			
			System.out.println("Last byte length  "+leftLen);
			System.out.println("\n");
			
			
		}
			System.out.println("End");
			
			
	}
	public static void main(String[] args) throws IOException {
		ServerMain main=new ServerMain();
		
	}
}
