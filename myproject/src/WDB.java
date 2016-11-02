import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


class myframe extends JFrame implements ActionListener{
	JPanel p1,p2;
	JLabel lstart;
	JButton bstart;
	JTextField tfstart;
	public myframe(){
		setTitle("시작 화면");
		setSize(300,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		p1 = new JPanel();
		p2 = new JPanel();
		lstart = new JLabel("이름 적고 시작하세요");
		tfstart = new JTextField(20);
		p1.add(lstart);
		p1.add(tfstart);
		bstart = new JButton("게임 시작");
		p2.add(bstart);
		bstart.addActionListener(this);
		add(p1,BorderLayout.CENTER);
		add(p2,BorderLayout.SOUTH);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JFrame yourframe = new JFrame("게임화면");
		yourframe.setSize(1000, 500);
		yourframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		yourframe.setLayout(new GridLayout(0,3));
		Object[] array = new Object[15];
		for(int i = 0;i<15;i++){
			array[i] = yourframe.add(new JButton("a"+i));
		}
		ImageIcon[] nope1 = new ImageIcon[7];
		for(int i = 0;i<nope1.length;i++){
			nope1[i] = new ImageIcon((i+1)+".jpg");
		}
		Object[] array1 = new Object[nope1.length];
		for(int i = 0;i<array1.length;i++){
			array1[i] = nope1[(int) (Math.random()*array1.length)];
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