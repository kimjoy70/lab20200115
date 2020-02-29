package UI.swing;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpinnerClick extends JFrame implements ChangeListener{
	JSpinner spin = new JSpinner();
	SpinnerNumberModel jsnm = new SpinnerNumberModel();
	public SpinnerClick() {
		spin.addChangeListener(this);
		jsnm.setMaximum(100);
		jsnm.setValue(10);
		jsnm.setStepSize(1);
		jsnm.setMinimum(1);
		spin.setModel(jsnm);
		this.add(spin);
		this.setSize(400, 300);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		new SpinnerClick();
	}
	@Override
	public void stateChanged(ChangeEvent ce) {
		// TODO Auto-generated method stub
		int pattern = (Integer)jsnm.getValue();
		///JOptionPane.showInputDialog("입력하세요.");
		System.out.println(pattern);
	}

}
