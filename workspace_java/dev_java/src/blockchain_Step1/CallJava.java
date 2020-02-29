package blockchain_Step1;

import java.io.*;

public class CallJava
{
   public static void main(String args[])
   {
      Process theProcess = null;
      BufferedReader inStream = null;
 
      System.out.println("CallJava.main() invoked");
 
      // call the Hello class
      try
      {
          theProcess = Runtime.getRuntime().exec("java Step1Chain");
      }
      catch(IOException e)
      {
         System.err.println("Error on exec() method");
         e.printStackTrace();  
      }
        
      // read from the called program's standard output stream
      try
      {
         inStream = new BufferedReader(
                                new InputStreamReader( theProcess.getInputStream() ));  
         System.out.println(inStream.readLine());
      }
      catch(IOException e)
      {
         System.err.println("Error on inStream.readLine()");
         e.printStackTrace();  
      }
   } 
} 
