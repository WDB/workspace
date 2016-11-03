import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class myframe extends JFrame implements ActionListener{
	JPanel p1,p2,p3;
	JLabel l1,l2;
	JTextField tf1,tf2;
	JButton b1;
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
		
		l2 = new JLabel("카드 숫자를 입력하자");
		
		tf2 = new JTextField(5);
		
		b1 = new JButton("게임 시작");
	
		b1.addActionListener(this);
		p1.add(l1);
		p1.add(tf1);
		p1.add(l2);
		p1.add(tf2);
		p1.add(b1);
		add(p1);
		
		p2 = new JPanel();
		p2.setLayout(new GridLayout(0,3));
		
		p2.setVisible(false);
		setResizable(false);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("버튼이 클릭됨");
		v = Integer.parseInt(tf2.getText());
		p1.setVisible(false);
		name = tf1.getText();
		
		this.setSize(1000,500);
		System.out.println(name);
		System.out.println(v+5);
		for(int i = 0;i<v;i++){
			p2.add(new JButton("버튼"+i));
		}
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