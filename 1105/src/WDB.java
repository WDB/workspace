import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;
public class WDB extends JFrame{
	String userid;//유저 아이디 
	int cards[] = new int[20];//카드 셔플
	
	
	//상단 부분
	JPanel p1,p2;
	int sco = 0;
	JLabel scores; // 상단의 점수 표시
	int sec = 0;
	JLabel timers; //상단의 시간 표시
	Timer abc = new Timer();//상단의 시간 초기화
	
	//본문
	ImageIcon ic1; // 뒷면 이미지 저장할 아이콘
	ImageIcon[] ics = new ImageIcon[20]; // 앞면 이미지들
	JLabel labelimage[] = new JLabel[20]; // 이미지들을 올릴 라벨들
	Timer maxtime = new Timer(); //패를 섞을 때 타이머 초기화
	Timer hidetime = new Timer(); // 패를 뒤집기 전에 패를 몇초간 보여줄지 타이머
	Timer timecheck = new Timer();		// 패 2개를 뒤집고나서 틀렸을 경우 몇초간 보여줄지 타이머
	JLabel check; //2개 패 찾기를 성공한 것을 누른건지 확인하기 위한 버튼
	JLabel first; //처음 선택된 버튼
	JLabel second; //두번째 선택된 버튼
	
	int twocheck; //버튼이 몇번 눌렸는지 체크하기
	int firstnumber; //첫번째 눌린 버튼에서 배열의 카드번호 저장하기 위해 
	int secondnumber; //두번째 눌린 버튼에서 배열의 카드번호 저장하기 위해
	int nopenumber;//몇번째 버튼인지 확인하기 위한 눌려진 버튼의 번호 저장
	ImageIcon selector;//눌려진 이미지 보여주기 위해
	
	//결과 팝업창
	JDialog result;
	JLabel text;
	JLabel score;
	JButton resultok;
	int cardscore = 0;
	
	public WDB(){
		//초기 생성자
		super("그림 맞추기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		p1 = new JPanel();
		
		setBackground(Color.green);
		setLayout(new BorderLayout());
		
		//상단 p1구현
		p1.setBackground(Color.YELLOW);
		timers = new JLabel(" "+sec+"초");
		scores = new JLabel(" "+sco+"만점");
		p1.add(timers);
		p1.add(scores);
		
		p2 = new JPanel2();
		
		resultok = new JButton("확인");
		setSize(600, 700);
		add(p1,BorderLayout.NORTH);
		add(p2,BorderLayout.CENTER);
		setVisible(true);
		
		
	}
	/*inner*/
	class JPanel2 extends JPanel{
		//본문 p2구현
		public JPanel2(){
				setBackground(Color.red);
				setLayout(new GridLayout(4,5,10,30));
				mixcard();
				setimage();
				setbuttonfirstimage();
				setbuttonname();
				hidebuttonimage();
				
				for(int i = 0;i<20;i++){
					labelimage[i].addMouseListener(new mouseclicker());
				}
				for(int i = 0;i<20;i++){
					add(labelimage[i]);
				}
		}
	}
	private class mouseclicker implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			check = (JLabel) e.getSource();
			if(check.getName() == "checked"){ //눌린 버튼이 check이면 성공한 버튼 눌린거면 실행 안하기
				twocheck = 0;
			}
			else if(twocheck == 0 || twocheck == 1){//카드가 뒤집어졌을 때 실행
				twocheck += 1; //카드 선택될 때마다 1카운트
				//첫번째 눌린 카드의 번호
				if(twocheck == 1){
					first = (JLabel) e.getSource();
					nopenumber = Integer.parseInt(first.getName()) - 1;
					selector = new ImageIcon(cards[nopenumber]+".jpg");
					first.setIcon(selector); //버튼 눌러진 이미지 저장하기
					
					firstnumber = cards[nopenumber]; //카드번호가 10이하면 그냥 저장
					if(cards[nopenumber] > 10){//카드번호가 10보다 크면 -10
						firstnumber = cards[nopenumber] - 10;
					}
				}
				//두번째 눌린 카드의 번호
				if(check.getName() == first.getName()){// 두번째 클릭이 처음클릭한 카드를 또 선택했으면 카운트0
					twocheck = 1;
				}
				else if(twocheck == 2){
					second = (JLabel) e.getSource(); //첫번째 눌린 버튼 객체 가져오기
					nopenumber = Integer.parseInt(second.getName()) - 1;
					selector = new ImageIcon(cards[nopenumber]+".jpg");
					second.setIcon(selector);
					
					secondnumber = cards[nopenumber];//카드번호가 10이하면 그냥 저장
					if(cards[nopenumber] > 10){ //카드번호가 10보다 크면 -10
						secondnumber = cards[nopenumber] - 10;
					}
					if(firstnumber == secondnumber){ //첫번째 두번째 선택된 카드를 비교하자.
						twocheck = 0; //2장 선택 시 다시 초기화
						first.setName("checked"); //성공이므로  선택 못하게 하기
						second.setName("checked"); //성공이므로  선택 못하게 하기
						
						int end = 0;//패 몇 개 맞췄는지 확인하기
						for(int i = 0;i<20;i++){
							if((labelimage[i].getName()).equals("checked")){
								end = end + 1;
								if(end == 20){ //20개 전부 맞춘다면
									abc.cancel(); //타이머 정지
									timers.setText(" " +sec+"초");
									sco = 0;
									scores.setText(" "+sco+"점");
									break;
								}
							}
						}
					}
					else{
						timecheck = new Timer();
						timecheck.scheduleAtFixedRate(new TimerTask(){//첫번째 두번째 카드 비교 후 틀리면 몇초간 보여주기

							@Override
							public void run() {
								// TODO Auto-generated method stub
								twocheck = 0; //카드 2선택되면 다시 초기화, 카드가 뒷면 보일 때까지 다른 거 못 건드림
								first.setIcon(ic1); //틀렸으므로 복구
								second.setIcon(ic1); // 틀렸으므로 복구
								timecheck.cancel(); //타이머 종료
							}
							
						}, 200,1); //시간이 지나면 카드 뒷면 보이기
					}
				}//두번째 눌린 카드 번호
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
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
		aa:for(int j = 0;j<20;j++){
				if(j == i){
					break aa;
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