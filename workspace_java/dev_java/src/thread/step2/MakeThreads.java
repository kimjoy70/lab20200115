package thread.step2;

import java.util.HashMap;
import java.util.Vector;

public class MakeThreads {

	public static void main(String[] args) {
		for(int loop=0;loop<3;loop++) {
			LoopingThread thread = new LoopingThread();
			thread.start();
		}
		System.out.println("Started looping threads..."+"You must stop this process after test...");
	}

}
class LoopingThread extends Thread{
	public void run() {
		int runCount = 100;
		while(true) {
			try {
				String string = new String("AAA");
				Vector<String> v = new Vector<>();
				for(int loop=0;loop<runCount;loop++) {
					v.add(string);
				}
				HashMap<String,Integer> hashMap = new HashMap<String,Integer>(runCount);
				for(int loop=0;loop<runCount;loop++) {
					hashMap.put(string+loop,loop);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}