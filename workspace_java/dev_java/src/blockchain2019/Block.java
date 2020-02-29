package blockchain2019;

import java.util.ArrayList;
import java.util.Date;

public class Block {
	//해쉬값을 담음
	public String hash;
	//이전 해쉬값을 담음
	public String previousHash;
	//데이터를 담음
	private String data; //our data will be a simple message.
	//시간정보를 담음
	private long timeStamp; //as number of milliseconds since 1/1/1970.
	//무엇을 증가하는 걸까?
	private int nonce;
	public String merkleRoot;
	public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.	
	//Block Constructor.
	//@param1 - 블록에 담을 데이터
	//@param2 - 이전해쉬값
	public Block(String data,String previousHash ) {
		this.data = data;
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		//Block을 인스턴스화 하면 생성자에서 파라미터로 받은 data와 이전해쉬값을 활용하여
		//calculateHash메소드에서 해쉬값을 만들어 줌.
		this.hash = calculateHash(); //Making sure we do this after we set the other values.
	}	
	//Block Constructor.  
	public Block(String previousHash ) {
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		
		this.hash = calculateHash(); //Making sure we do this after we set the other values.
	}	
	//해쉬값을 생성하는 메소드 구현
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				previousHash +
				Long.toString(timeStamp) +
				Integer.toString(nonce) + 
				data 
				);
		return calculatedhash;
	}
/*
 * 비트코인 네트워크에서 노드들은 네트워크상의 블록체인을 서로 공유한다. 그럼 블록체인 상에서 악의적인 노드가 데이터를 위변조 하거나 네트워크를 위협 할 때 어떻게 대처할까?
모든 노드가 올바른 블록체인을 공유하고 있다는 것을 입증하기 위해서 합의 알고리즘을 사용한다. 다양한 합의 알고리즘이 있는데 비트코인에서는 PoW(Proof of Work)라는
 합의 알고리즘을 사용한다. PoW란 컴퓨팅 파워를 이용해 블록의 hash값을 추적하여 블록체인 네트워크에 새로운 블록을 추가하는 방식인 합의 알고리즘이다.
이 과정을 채굴(Mining)이라고 부르는데 PoW 방식의 알고리즘은 다량의 컴퓨팅 파워를 요구하고 시간 또한 오래 걸린다. 그래서 공격자는 그만큼 많은 컴퓨팅 파워를 요구하게 된다.
hash값의 추적은 hash값 첫 부분의 0 개수를 추적하는 과정이며 이를 위해서는 계속해서 다른 변수값을 블록에 넣으면서 조건을 만족할 때 까지 hashing하는 것이다.
 0의 개수가 많아질수록 추적은 어려워지며 ‘난이도(difficulty)가 높다’고 말한다.
위에서 말한 ‘다른 변수값’이란 비트코인에서 nonce라고 불리며 우리 코드에도 적용시켜 보자.
hash를 계산할 때, nonce값을 추가하여 hash값을 만들어 준다. 이 nonce 값은 블록을 채굴하기 위해 사용되는 값이다. 이 코드에서는 nonce 값을 0에서부터 1씩 증가시키며,
 특정 조건을 만족시킬 때 까지 hashing과정을 거치게 된다.
mineBlock이라는 메서드와 파라미터로 difficulty(난이도) 라는 것이 추가되었다. 이 메서드는 difficulty를 이용해 hashing된 값의 앞 자리에서 부터 나타나는 0의 개수를 정한다. 
difficulty 값이 높아지면 그만큼 0의 개수도 많아지고, 조건(0의 개수)에 맞는 값을 찾아가는 데에는 더욱 오랜 시간이 걸리게 된다.
앞 자리의0의 개수가 많아져야 할 수록 그에 맞는 조건을 맞추기는 어렵다. 위의 예제에서는 timestamp, nonce, previousHash 등의 집합으로 hashing이 이루어진다. 
쉽게 설명하기 위해, 주사위 예시를 들 수 있다(마스터링 비트코인 참고). 주사위 두 개를 던져서 나올 값의 합이 특정 목표값 보다 낮은 숫자가 나올 확률을 생각해보자. 
만약 목표값이 12이면 (6, 6) 을 던지지 않는 이상 성공한다. 거의 100%에 가깝다. 하지만 목표값이 11, 10, 9… 점점 떨어지면 확률 또한 기하급수적으로 떨어진다. 
만약 목표값이 2라면 주사위를 던져 (1, 1)이 나와야 하므로 확률은 1/36, 약 2%이다. 이런식으로, hashing 과정에서 0의 개수가 늘어난다는 것은, 
목표값에 만족할 수 있는 확률 또한 기하급수적으로 낮아진다는 것이다.
이제 채굴과 검증 과정을 한 번 확인해 보도록 하자.
difficulty를 5로 설정하고 3개의 블록을 채굴하는 데에 필자의 노트북으로 6초 정도가 걸렸다. difficulty가 높아질 때 마다 채굴하는 데에 시간이 오래 걸리고, 
cpu 팬이 팽팽 도는 소리를 들을 수 있을 것이다.
만약 악의적인 노드가 데이터를 위변조 하려고 한다면 위변조된 블록은 유효하지 않을 것이다.(Invalid)
기존의 체인에 붙지 않을 것이고 악의적인 노드가 자체적인 체인을 만들려고 한다해도,
이것은 기존의 체인보다 긴 체인을 만들 수 없다.
 * 	1.difficulty에 따라 target을 생성한다.
    2.target은 difficulty의 숫자만큼 0으로 구성한다.
    3.블록의 해시는 [이전해시 + timestamp + nonce + data]의 조합으로 해시를 만든다.
    4.생성한 hash가 target과 동일한 값으로 시작하면 채택!
       동일하지 않으면 nonce를 1증가 시키고 3번 ~ 4번을 반복한다.
 */
	public void mineBlock(int difficulty) {
		System.out.println("mineBlock호출  ==> difficulty:"+difficulty);
		String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
		System.out.println("변수 target에 담긴 값  ==> target:"+target);
		System.out.println("while문에서 비교 상대값  ==> hash.substring( 0, difficulty):"+hash.substring( 0, difficulty));
		//if(!hash.substring( 0, difficulty).equals(target)) {
		while(!hash.substring( 0, difficulty).equals(target)) {
			System.out.println("while nonce:"+nonce);
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}
	/*
	 * 트랜잭션을 구현하는 것은 완성 되었고 이제 이것을 블록체인 안에 넣기 위한 구현단계가 남아 있다.
	 * 앞서 트랜잭션에 필요한 데이터들을 하나의 ArrayList로 넣었다.
	 * 
	 * 만약 하나의 블록안에 많은 개수의 트랜잭션이 들어가면 어떻게 될까?
	 * 당연히 블록의 크기는 커질 것이고 특정 트랜잭션을 찾기 위한 시간은 길어질 것이다.
	 * 이를 위해 머클트리 자료구조가 쓰인다.
	 * 
	 * 머클트리의 특징
	 * 1) 이진트리와 유사하다.
	 * 2) 거래가 아무리 많아도 root node의 크기는 32비트이다.
	 * 3) leaf node만이 transaction의 정보를 가진다.
	 * 4) parent node는 child node들의 hash값을 합친 후 다시 hasing 한 결과값이다.
	 */
	//Add transactions to this block
	public boolean addTransaction(Transaction transaction) {
		//process transaction and check if valid, unless block is genesis block then ignore.
		if(transaction == null) return false;		
		if((previousHash != "0")) {
			if((transaction.processTransaction() != true)) {
				System.out.println("Transaction failed to process. Discarded.");
				return false;
			}
		}
		transactions.add(transaction);
		System.out.println("Transaction Successfully added to Block");
		return true;
	}	
}
