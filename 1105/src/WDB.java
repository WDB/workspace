import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;
public class WDB extends JFrame{
	String userid;//���� ���̵� 
	int cards[] = new int[20];//ī�� ����
	
	
	//��� �κ�
	JPanel p1,p2;
	int sco = 0;
	JLabel scores; // ����� ���� ǥ��
	int sec = 0;
	JLabel timers; //����� �ð� ǥ��
	Timer abc = new Timer();//����� �ð� �ʱ�ȭ
	
	//����
	ImageIcon ic1; // �޸� �̹��� ������ ������
	ImageIcon[] ics = new ImageIcon[20]; // �ո� �̹�����
	JLabel labelimage[] = new JLabel[20]; // �̹������� �ø� �󺧵�
	Timer maxtime = new Timer(); //�и� ���� �� Ÿ�̸� �ʱ�ȭ
	Timer hidetime = new Timer(); // �и� ������ ���� �и� ���ʰ� �������� Ÿ�̸�
	Timer timerCardCheck = new Timer();		// �� 2���� ���������� Ʋ���� ��� ���ʰ� �������� Ÿ�̸�
	JLabel check; //2�� �� ã�⸦ ������ ���� �������� Ȯ���ϱ� ���� ��ư
	JLabel first; //ó�� ���õ� ��ư
	JLabel second; //�ι�° ���õ� ��ư
	
	int twocheck; //��ư�� ��� ���ȴ��� üũ�ϱ�
	int firstnumber; //ù��° ���� ��ư���� �迭�� ī���ȣ �����ϱ� ���� 
	int secondnumber; //�ι�° ���� ��ư���� �迭�� ī���ȣ �����ϱ� ����
	int nopenumber;//���° ��ư���� Ȯ���ϱ� ���� ������ ��ư�� ��ȣ ����
	ImageIcon selector;//������ �̹��� �����ֱ� ����
	
	//��� �˾�â
	JDialog result;
	JLabel text;
	JLabel score;
	JButton resultok;
	int cardscore = 0;
	
	public WDB(){
		//�ʱ� ������
		super("�׸� ���߱�");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p1 = new JPanel();
		
		setBackground(Color.green);
		setLayout(new BorderLayout());
		
		//��� p1����
		p1.setBackground(Color.YELLOW);
		timers = new JLabel(" "+sec+"��");
		scores = new JLabel(" "+sco+"����");
		p1.add(timers);
		p1.add(scores);
		
		p2 = new JPanel2();
		
		resultok = new JButton("Ȯ��");
		setSize(600, 700);
		add(p1,BorderLayout.NORTH);
		add(p2,BorderLayout.CENTER);
		setVisible(true);
		
		
	}
	/*inner*/
	class JPanel2 extends JPanel{
		//���� p2����
		public JPanel2(){
				setBackground(Color.red);
				setLayout(new GridLayout(4,5,10,30));
				mixcard();
				setimage();
				setbuttonfirstimage();
				setbuttonname();
				hidebuttonimage();
				
				for(int i = 0;i<20;i++){
					add(labelimage[i]);
				}
		}
	}
	public void hidebuttonimage() {
		// TODO Auto-generated method stub
		for ( int i = 0; i<20; i++){
			 labelimage[i].setIcon(ic1);
		}	
	}
	public void setbuttonname() {
		// TODO Auto-generated method stub
		for ( int i = 0; i<20; i++){
			 labelimage[i].setName(Integer.toString(i+1));
		}	
	}
	public void setbuttonfirstimage() {
		// TODO Auto-generated method stub
		for(int i = 0;i<cards.length;i++){
			labelimage[i] = new JLabel(ics[i]);
		}
		
	}
	public void setimage() {
		// TODO Auto-generated method stub
		ic1 = new ImageIcon("0.jpg");
		for(int i = 0;i<cards.length;i++){
			ics[i] = new ImageIcon(cards[i]+".jpg");
		}
	}
	public void mixcard() {
		// TODO Auto-generated method stub
		int i = 0;
		int randum = 0;
		while(true){
			randum = (int)(Math.random()*20+1);
			cards[i] = randum;
			for(int j = 0;j<cards.length;j++){
				if(j == i){
					break;
				}
				if(cards[j] == randum){
					i = i - 1;
				}
			}
			i = i+1;
			if(i == -1) i = 0;
			if(i == 20)break;
 		}
	}
	public static void main(String args[]){
		new WDB();
	}
}