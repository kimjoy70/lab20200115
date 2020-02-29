package blockscenario1;


public class Input {
	public String OutputId; // 트랜잭션아웃풋에 대한 참조
	public Output UTXO; // 미사용 트랜잭션 출력을 포함
	
	public Input(String OutputId) {
		this.OutputId = OutputId;
	}
}
