package thread.step2;

public class BankAccount {
	//처음에 잔고 100달러에서 시작합니다.
	private int balance = 100;
	public int getBalance() {
		return balance;
	}
	public void withdraw(int amount) {
		balance = balance - amount;
	}
}
