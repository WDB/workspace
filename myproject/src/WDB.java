import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


class myframe extends JFrame implements ActionListener{
	JPanel p1,p2;
	JLabel lstart;
	JButton bstart;
	JTextField tfstart;
	public myframe(){
		setTitle("���� ȭ��");
		setSize(300,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		p1 = new JPanel();
		p2 = new JPanel();
		lstart = new JLabel("�̸� ���� �����ϼ���");
		tfstart = new JTextField(20);
		p1.add(lstart);
		p1.add(tfstart);
		bstart = new JButton("���� ����");
		p2.add(bstart);
		bstart.addActionListener(this);
		add(p1,BorderLayout.CENTER);
		add(p2,BorderLayout.SOUTH);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JFrame yourframe = new JFrame("����ȭ��");
		yourframe.setSize(1000, 500);
		yourframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		yourframe.setLayout(new GridLayout(0,3));
		for(int i = 0;i<15;i++){
			yourframe.add(new JButton("a"));
		}		
		yourframe.setVisible(true);
		this.setVisible(false);
	}
}

public class WDB{
	public static void main(String args[]){
		new myframe();
	}
}