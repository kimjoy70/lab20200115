package UI.swing;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class JTreeExam extends JFrame {
	public JTreeExam(){
		initDisplay();
	}
	public void initDisplay(){
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
//		DefaultMutableTreeNode tree1 = new DefaultMutableTreeNode("자바사랑");
		DefaultMutableTreeNode tree1[] = new DefaultMutableTreeNode[3];
		String[] s_list = {"홍길동", "강감찬", "이순신"};
		DefaultMutableTreeNode tree2[] = new DefaultMutableTreeNode[2];
		String[] s_list2 = {"홍길동전", "행주대첩"};
		for(int i=0;i<s_list.length;i++)
		{
			tree1[i] = new DefaultMutableTreeNode(s_list[i]);
			root.add(tree1[i]);
		}
		for(int i=0;i<s_list2.length;i++)
		{
			tree2[i] = new DefaultMutableTreeNode(s_list2[i]);
			tree1[0].add(tree2[i]);
		}
		JTree tree = new JTree(root);
		this.add(tree);
		this.setSize(300, 150);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new JTreeExam();
	}

}
