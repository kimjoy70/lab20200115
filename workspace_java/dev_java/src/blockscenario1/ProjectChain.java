package blockscenario1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



/*
 * 각 프로젝트들 마다 있는 채인
 */
public class ProjectChain {
	// 프로젝트가 생성 되었을 시 초기화
	public static final String projectID = null;
	public static List<Block> projectChain = new ArrayList<Block>();
	public static Map<String, Output> UTXOs = new HashMap<>();
	public static int difficulty = 3;
	//
	public static float minimumTransaction = 0.1f;
	public static List<Map<String, Wallet>> wallets = new ArrayList<>();
	
	
}
