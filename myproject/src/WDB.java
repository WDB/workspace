import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class myframe extends JFrame implements ActionListener{
	JPanel p1,p2,p3;
	JLabel l1,l2;
	JTextField tf1,tf2;
	JButton b1,v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15;
	int v;
	String name;
	public myframe(){
		v = 0;
		setTitle("그림맞추기게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(180,200);
		
		p1 = new JPanel();
		
		l1 = new JLabel("이름을 입력하세요");
		
		tf1 = new JTextField(10);
		
		
		b1 = new JButton("게임 시작");
	
		b1.addActionListener(this);
		p1.add(l1);
		p1.add(tf1);

	
		p1.add(b1);
		add(p1);
		
		p2 = new JPanel();
		p2.setLayout(new GridLayout(0,3));
		
		p2.setVisible(false);
		setResizable(false);
		setVisible(true);
	}
	private class mylistener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.getActionCommand().equals("버튼1")){
				v1 = new JButton(new ImageIcon("1.jpg"));
				p2.add(v1);
				System.out.println("wef");
			}
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("버튼이 클릭됨");
		
		p1.setVisible(false);
		name = tf1.getText();
		
		this.setSize(1000,500);
		System.out.println(name);
		
		v1 = new JButton("버튼1");
		v1.addActionListener(new mylistener());
		
		p2.add(v1);
		p2.setVisible(true);
		setResizable(true);
		add(p2);
	}
}

public class WDB{
	public static void main(String args[]){
		new myframe();
	}
}