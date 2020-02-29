package design.book;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class ProcessIDList {

	public static void main(String[] args) throws Exception{
		String processname="notepad.exe";  // 가져올 프로세스
		  ProcessBuilder pb2 = new ProcessBuilder("cmd.exe","/C","tasklist /FI \"IMAGENAME eq " + processname + "\" /FO TABLE /NH");
		  Process myproc = pb2.start();
		  
		  InputStream errorOutput = new BufferedInputStream(myproc.getErrorStream(), 1000000);
		  InputStream consoleOutput = new BufferedInputStream(myproc.getInputStream(), 1000000);
		  
		  int ch;
		  String processlist = "";
		  while ((ch = consoleOutput.read()) != -1) {
		      processlist = processlist + (char) ch;
		  }
		  
		  String []processarr = processlist.split("\n");
		  String processtemp = "";
		  String pid ="";
		  ArrayList<String> al = new ArrayList();
		  for( int j = 0; j< processarr.length;j++){
		   
		   processtemp = processarr[j];
		   if(processtemp.length() < 2 )continue;
		   
		   pid = processtemp.substring(processname.length(), processname.length()+23);
		   pid = pid.trim();
		   System.out.println( "processlist3 =="+ pid);
		   al.add(pid);
		  }
		  int exitCode = myproc.waitFor(); 
		  
	}

}
