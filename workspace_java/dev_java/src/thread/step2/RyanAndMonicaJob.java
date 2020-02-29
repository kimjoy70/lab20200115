package thread.step2;
/*
 * 아래 스레드 두 개(라이언과 모니카)가 객체 하나(은행계좌)를 공유할 때 생길 수 있는 
 * 문제를 보여주는 예제가 있습니다.
 * 이 코드에는 BankAccount와 RyanAndMonicaJob 이라는 클래스 두 개가 있습니다.
 * 
 * RyanAndMonicaJob 클래스에는 Runnable을 구현하고 라이언과 모니카가 모두 하는 
 * 행동(잔고를 확인하고 인출하는 행동)이 들어 있습니다.
 * 하지만 두 스레드는 모두 잔고를 확인하고 인출하는 사이에 한번 대기 상태에 들어갑니다.
 * RyanAndMonicaJob 클래스에는 BankAccount 유형의 인스턴스 변수가 있는데
 * 이 인스턴스 변수는 둘이 공유하고 있는 계좌를 나타냅니다.
 * 
 * 문제제기
 * makeWithdrawal()메소드에서 항상 인출 전에 잔고를 확인하는데도 계좌에서 남아있는
 * 잔고보다 돈을 더 많이 뽑는 문제가 생깁니다.
 * 라이언이 잔고가 충분한지 확인한 다음 잠이 듭니다.
 * 그 동안 모니카가 끼어들어서 잔고를 확인합니다. 그러면 돈이 충분히 남아있겠죠?
 * 하지만 모니카는 라이언이 잠에서 깨어나서 돈을 인출하리라는 것은 전혀 알수가 없습니다.
 * 
 * 모니카가 잠이 듭니다.
 * 라이언이 일어나서 돈을 뽑습니다.
 * 모니카가 일어나서 돈을 뽑습니다. 그런데  큰문제가 생겼습니다.
 * 모니카가 잔고를 확인하고 잠시 잠든 사이에 라이언이 일어나서 돈을 인출했습니다.
 * 
 * 모니카가 전에 잔고를 확인했던 것은 사실 무의미 합니다. 라이언이 이미 잔고를 확인하고
 * 돈을 인출할 준비를 하고 있어으니까요
 * 따라서 라이언이 잠든 사이에 즉 라이언이 일을 마치기 전에 모니카가 계좌에 접근할 수 없게
 * 만들어야 합니다. 그 반대도 마찬가지 입니다.
 */
public class RyanAndMonicaJob implements Runnable {
	private BankAccount account = new BankAccount();
	/*
	 * 한 스레드는 라이언을 다른 스레드는 모니카를 나타냅니다.
	 * 두 스레드에서 모두 연속적으로 잔고를 확인하고 인출을 합니다.
	 * 물론 잔고가 뽑으려는 금액보다 많을 때만 인출을 하지요.
	 */
	@Override
	public void run() {
	/*
	 * run메소드에서는 스레드에서 순환문을 돌리면서 매번 반복할 때 마다 인출을 시도 합니다.
	 * 돈을 인출하고 나서 잔고를 다시 확인하여 잔고가 마이너스가 되었는지 확인합니다.	
	 */
		for(int x=0;x<10;x++) {
			makeWithdrawl(10);
			if(account.getBalance() < 0) {
				System.out.println("overdrawn!");
			}
		}
	}
	private synchronized void makeWithdrawl(int amount) {
//	private void makeWithdrawl(int amount) {
	/*
	 * 계좌 잔고를 확인하고 돈이 부족하면 메시지를 출력합니다. 부족하지 않다면 대기상태로 
	 * 넘어간 다음에 인출 과정을 끝냅니다.
	 * 라이언이 잔고를 확인하고 잠깐 잠들었다가 깨서 돈을 뽑는 것과 같습니다.	
	 */
		if(account.getBalance() >=amount) {
			System.out.println(Thread.currentThread().getName()+" is about to withdraw");
			try {
				System.out.println(Thread.currentThread().getName()+" is going to sleep");
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+" woke up.");
			account.withdraw(amount);
			System.out.println(Thread.currentThread().getName()+" completes the withdrawl");
		}
		else {
			System.out.println("Sorry, not enough for "+Thread.currentThread().getName());
		}
	}
	public static void main(String args[]) {
		RyanAndMonicaJob theJob = new RyanAndMonicaJob();
		//똑같은 Runnable객체를 전달하여 스레드 두 개를 만듭니다.
		//그러면 두 스레드에서 같은 Runnable클래스에 들어있는 계좌 인스턴스 하나를 사용하게 되죠.
		Thread one = new Thread(theJob);
		Thread two = new Thread(theJob);
		//스레드에 이름을 부여하고 시작시킵니다.
		one.setName("Ryan");
		two.setName("Monica");
		one.start();
		two.start();
	}
}
