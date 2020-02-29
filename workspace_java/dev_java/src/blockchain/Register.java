package blockchain;
import java.io.Serializable;

import blockchain.util.CommonSet;


public class Register implements Serializable {
	private static final long serialVersionUID = 7262040228088375698L;
	public Wallet createMoneyWallet() {
		Wallet wallet = new Wallet();
		wallet.generateKeyPair();
		return wallet;
	}
	public Wallet createProjectWallet(String WalletID) {
		CommonSet commonSet = CommonSet.getInstance();
		Wallet wallet = new Wallet();
		wallet.generateKeyPair();
		commonSet.projectWallets.put(WalletID, wallet);
		return wallet;
	}
	public Project createProject(String projectCode, String WalletID) {
		CommonSet commonSet = CommonSet.getInstance();
		Project project = new Project();
		Wallet wallet = null;
		try {
			wallet = commonSet.getProjectWallet(WalletID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		project.projectCode = projectCode;
		project.projectPublicKey = wallet.getPublicKey().toString();
		commonSet.projects.put(projectCode, project);
		return project;
	}
}
