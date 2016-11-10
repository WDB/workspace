

import java.awt.*;

import javax.swing.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;


public class WDB extends JFrame{

	File path = new File("");   // 게임 경로 찾기
	String userId ;			// 게임유저 아이디 받아오기
	
	int cardrandom[] = new int [20];		// 카드 랜덤번호가 들어갈 배열

	
	// 패널 1 메뉴부분
	JPanel panel1;		
	int score=0;
	JLabel labelScore;			// 패널1의 점수
	int sec=0;
	JLabel labelTimer; 		// 시간초 보여줄 패널1의 라벨
	Timer timerSec = new Timer();		// 패널1에 보여줄 라벨의 타이머 초기화
	JButton buttonStart;	  		// 게임 시작 버튼

	
	// 패널 2 게임구현부분
	JPanel panel2;		
	ImageIcon imageBack ;			// 뒷면 이미지 저장할 아이콘
	ImageIcon imageIcon[]=new ImageIcon[20] ;			// 앞면 이미지 저장할 20개 아이콘들
	int startCheck;		// 새게임버튼 안누르고 그림 누를경우엔 0, 새게임 누르고 패누를땐 1
	JLabel labelImage[]=new JLabel[20] ;		// 이미지들을 올려둘 버튼 20개
	Timer timerMix = new Timer();		// 패를 섞을때 속도 조절위한 타이머 초기화
	Timer timerHide = new Timer();		// 패를 뒤집기전에 패를 몇초간 보여줄지 타이머
	Timer timerCardCheck = new Timer();		// 패 2개를 뒤집고나서 틀렸을 경우 몇초간 보여줄지 타이머
	JLabel labelConfirmedCheck;   	// 2개 패 찾기를 성공한것을 누른건지 확인하기위한버튼
	JLabel labelSelectedFirst;		// 처음 선택된 버튼
	JLabel labelSelectedSecond;		// 두번째 선택된 버튼
	int selectedTwoCardCheck;		// 버튼이 몇번 눌렷는지 체크하기
	int firstCardNumber;		// 첫번째 눌린 버튼에서 배열의 카드번호 저장하기위해
	int secondCardNumber;	// 두번째 눌린 버튼에서 배열의 카드번호 저장하기위해
	int selectedImageNumber ;		// 몇번째 버튼인지 확인하기 위한 눌려진 버튼의 번호 저장
	ImageIcon selectedImage ;		// 눌려진 이미지 보여주기위해
	

	// 결과 팝업창
	JDialog dialogResult;
	JLabel labelResultText;
	JLabel labelResultScore;
	JButton buttonResultOK;
	int cardPuzzleScore=0;
	
	
	
	public WDB(){					// 초기 생성자
	
		super("CardPuzzle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel1 = new JPanel1();
		panel2 = new JPanel2();
		
		setBackground(Color.green);
		setLayout(new BorderLayout());
		startCheck=0;

		buttonResultOK = new JButton("확    인");
		buttonResultOK.setFont(new Font("GOTHIC",Font.BOLD , 25));
		buttonResultOK.addActionListener(new EventHandlerResultOK());
		
		dialogResult = new JDialog(this, "게임 결과");
		dialogResult.setLayout(new GridLayout(3,1));
		dialogResult.setBounds(230, 300, 200, 100);
		dialogResult.add(labelResultText);
		dialogResult.add(labelResultScore);
		dialogResult.add(buttonResultOK);
		
		add(panel1, BorderLayout.NORTH);
		add(panel2, BorderLayout.CENTER);

		setSize(600,700);
		setVisible(true);    
	}
	
	
	class JPanel1 extends JPanel{		// 버튼, 라벨패널
		
		public JPanel1(){
			
				setBackground(Color.green);
	            setLayout(new GridLayout(1,3,30,5));   // 패널 레이아웃 설정
	        
	            buttonStart =new JButton("New Game!!");

	        	labelTimer = new JLabel("  "+sec+" 초");	
	            labelTimer.setFont(new Font("GOTHIC",Font.BOLD , 20));
	    		labelTimer.setHorizontalAlignment(NORMAL);
	    		
	            labelScore = new JLabel("점수 :  "+score + "점 ");
	            labelScore.setFont(new Font("GOTHIC",Font.BOLD , 20));
	            labelScore.setHorizontalAlignment(NORMAL);
	            
	    		labelResultText = new JLabel();
	    		labelResultText.setHorizontalAlignment(NORMAL);
	    		labelResultScore = new JLabel();
	    		labelResultScore.setHorizontalAlignment(NORMAL);
	            
	        	add(labelScore);
	            add(buttonStart);
	            add(labelTimer);

	            buttonStart.addActionListener(new MyActionListenerNewGame());
	            
		}			
	}
	// 패널2	
	
	class JPanel2 extends JPanel{		// 게임 구현 패널
		
		public JPanel2(){
		
			setBackground(Color.green);
			setLayout(new GridLayout(4,5,10,30));
			
			mixNumber();		// 패섞기
			setImage();			// 이미지 받아오기
			setButtonFirstImage();		// 버튼에 이미지 씌우기
			setButtonName();			// 버튼에 각각 이름 붙이기
			
			for(int i =0; i<20;i++){
				labelImage[i].addMouseListener(new jjacMouseListener());
			}
			
			for(int i =0; i<20;i++){
				add(labelImage[i]);
			}
		}
	}
	// 패널2
	
	

	class MyActionListenerNewGame implements ActionListener {    //  게임 시작버튼
        @Override
        public void actionPerformed(ActionEvent e) {
        	
        	setButtonName(); 
        	startCheck=1; 
        	sec = 0;
        	score = 0;
    		labelTimer.setText("  "+sec+" 초");			// 라벨 0으로 초기화
    		labelScore.setText("점수 :  "+score + "점 ");			// 라벨 0으로 초기화
    		
        	timerMix.cancel();	
    	  	timerMix = new Timer();		// 타이머 객체 초기화
    	  	timerHide.cancel();	
    		timerHide = new Timer();		// 타이머 객체 초기화
          	timerSec.cancel();				// 타이머 객체 없애기
    	  	timerSec = new Timer();    // 타이머 객체 초기화
    	  	
        	timerMix.scheduleAtFixedRate(new TimerTask() { 		// 패 섞는 모션
        		int i=0;
				public void run() {
					mixNumber();
					setImage();
					setButtonResetImage();
					i=i+1;
					if(i==20)timerMix.cancel();			// 20번 섞었으면 타이머 종료시키기
				}
			}, 0,50);			// 0초후 실행해서 0.005초간격으로 섞기
 			
        	
    		timerHide.scheduleAtFixedRate(new TimerTask() {		// 패 4초 보여준 후 뒤집는 모션
    			public void run() {
    				hideButtonImage();
    				timerHide.cancel();
    			}
    		}, 2000, 1); 		// 2초후 실행
        	

        	timerSec.scheduleAtFixedRate(new TimerTask() { 		//  게임시작후 시간초
        
				public  void run() {
					labelTimer.setText("  "+sec+" 초");	
					sec=sec+1;
				}
			}, 2000,1000);		// 2초후 1초 간격으로 실행
        
        }
     }
	// 게임 시작버튼
 	
	private class jjacMouseListener implements MouseListener{		// 두개 카드 확인 및 결과 표시
		public void mouseClicked(java.awt.event.MouseEvent e) {		
		}
		public void mouseEntered(java.awt.event.MouseEvent e) {
		}
		public void mouseExited(java.awt.event.MouseEvent e) {	
		}
		public void mousePressed(java.awt.event.MouseEvent e) {		
			labelConfirmedCheck = ((JLabel)e.getSource());
        	if(labelConfirmedCheck.getName()=="checked"){    // 눌린 버튼이 checked이면 성공한버튼 눌린거면 실행안하기
            	selectedTwoCardCheck =0;    // 성공한 패를 눌렀으므로 카운트 다시 0으로
            	
        	}else if(startCheck==1 && (selectedTwoCardCheck==0 || selectedTwoCardCheck==1)){  
        		// 카드가 뒤집어 졌을때만 실행하기위해

        			selectedTwoCardCheck += 1;		 // 카드 선택 될때마다 카운트 + 1
        	
        			// 첫번째 눌린 카드 번호
        			if(selectedTwoCardCheck==1){		
        				
      	
        				labelSelectedFirst=((JLabel)e.getSource());  // 첫번째 눌린 버튼객체 가져오기
        				selectedImageNumber =Integer.parseInt(labelSelectedFirst.getName()) -1;  // 이름이 string 이므로 int로 변환
        				selectedImage = new ImageIcon(path +"pic/card/"+cardrandom[selectedImageNumber]+".jpg"); 
        				labelSelectedFirst.setIcon(selectedImage);     // 버튼 눌려진 이미지 보여주기
        		 
        				firstCardNumber = cardrandom[selectedImageNumber];	// 카드 번호가 10이하면 그냥 저장
        				if(cardrandom[selectedImageNumber]>10)			// 카드 번호가 10보다 크면 -10
        					firstCardNumber = cardrandom[selectedImageNumber]-10; 
        			}// 첫번째 눌린 카드 번호

        			// 두번째 눌린 카드 번호
    				if(labelConfirmedCheck.getName()==labelSelectedFirst.getName()){		// 두번째 클릭이 처음클릭한 카드를 또 선택했으면 카운트0
    					selectedTwoCardCheck =1;
    				}else if(selectedTwoCardCheck==2	){			
        				labelSelectedSecond=((JLabel)e.getSource());  // 첫번째 눌린 버튼객체 가져오기
        				selectedImageNumber =Integer.parseInt(labelSelectedSecond.getName()) -1;  // 이름이 string 이므로 int로 변환
        				selectedImage = new ImageIcon(path +"pic/card/"+cardrandom[selectedImageNumber]+".jpg"); 
        				labelSelectedSecond.setIcon(selectedImage);     // 버튼 눌려진 이미지 보여주기
        				
        				secondCardNumber = cardrandom[selectedImageNumber];	// 카드 번호가 10이하면 그냥 저장
        				if(cardrandom[selectedImageNumber]>10)				// 카드 번호가 10보다 크면 -10
        					secondCardNumber = cardrandom[selectedImageNumber]-10; 
        				
        				if(firstCardNumber==secondCardNumber ){   	// 첫번째 두번째 선택된 카드 비교
        					selectedTwoCardCheck =0;		// 카드 2선택되면 다시  초기화
        					labelSelectedFirst.setName("checked"); 				// 성공이므로 버튼 이름 50으로 해서 선택못하게 하기
        					labelSelectedSecond.setName("checked"); 			// 성공이므로 버튼 이름 50으로 해서 선택못하게 하기
        			 
        					int end=0;  // 패 몇개 맞췄는지 확인하기 
        					for(int i=0; i<20; i++){		 // 패 몇개 맞췄는지 확인하기 
        						if((labelImage[i].getName()).equals("checked")){
        							end=end+1;
        							if (end==20){		// 20개 전부 맞았는지 체크
        								
        								timerSec.cancel();		//  타이머 정지
        								labelTimer.setText("  "+sec+" 초");	
        								if(sec>100){  // 100초 넘도록 못 풀면 100초라고 본다 점수는 고로 0점
        									sec=100;
        								}
        								cardPuzzleScore = 100-sec;
        								labelScore.setText("점수 :  "+cardPuzzleScore+" 점");
        								
        								dialogResult();
        								break;
        							}
        						}
        					}
        					
        				}else{
        					timerCardCheck = new Timer();
        					timerCardCheck.scheduleAtFixedRate(new TimerTask() { 	//첫번째 두번째 카드비교후 틀리면 몇초보여주기간 
        	
        						public void run() {
        							selectedTwoCardCheck =0;		// 카드 2선택되면 다시  초기화 , 카드가 뒷면보일때까지 다른것 선택못하게하기
        							labelSelectedFirst.setIcon(imageBack); 				// 틀렸으므로 첫번째 카드 다시 뒤집기
        							labelSelectedSecond.setIcon(imageBack); 			// 틀렸으므로 두번째 카드 다시 뒤집기
        							timerCardCheck.cancel();				// 뒤집고 타이머 종료
        						}
        					}, 300,1);				// 0.4초후 카드 뒷면 보이게 하기.
        				} 
        			}// 두번째 눌린 카드 번호		
        	}
			
		}
		public void mouseReleased(java.awt.event.MouseEvent e) {
		}
	}
	// 두개 카드 확인 및 결과 표시

	class EventHandlerResultOK implements ActionListener{    // 게임결과확인버튼으로 DB에 점수 저장하기  // 전부 주석처리해도된다.
		
		public void actionPerformed(ActionEvent e){	
			
			dialogResult.dispose();   // 확인버튼 있는 다이얼로그창 없애기

			int ReceivedDBScore=0;
			
			String driver = "oracle.jdbc.driver.OracleDriver";
	        String url = "jdbc:oracle:thin:@111.222.253.124:1521:XE";   // 자신의 오라클 아이피로해야한다.

	        Connection con = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;   // 리턴받아 사용할 객체 생성
	     
   	        String querySelect = "select id, jjackscore from USER_INFO where id=?"; 	 
										// SUTDASCORE 검색   // 자신의 오라클DB에 맞게 설정해야한다.
	        
	        try{													// select
	            Class.forName(driver);
	            con = DriverManager.getConnection(url, "aaa", "aaa");
	            pstmt = con.prepareStatement(querySelect);
	           
	            pstmt.setString(1, userId); 
	            pstmt.executeUpdate();		// 쿼리문 실행
	            
	            rs = pstmt.executeQuery(); // 리턴받아온 쿼리 내용을 객체생성
	            while(rs.next()){
	                ReceivedDBScore = rs.getInt("jjackscore");
	            }
	            
	        }catch(Exception e1){
	            System.out.println(e1.getMessage());
	        }finally{
	            try {
	                rs.close(); 
	                pstmt.close(); 
	                con.close();   // 객체 생성한 반대 순으로 사용한 객체는 닫아준다.
	            } catch (Exception e2) {}
	        }

	        
	        String queryInsert = "UPDATE user_info SET jjackscore=? where id=?";
	        try{										// insert
	        	//  Class.forName(driver);  // 드라이버 로딩
	            con = DriverManager.getConnection(url, "aaa", "aaa"); // DB 연결   // 자신의 오라클DB에 맞게 설정해야한다.
	            pstmt = con.prepareStatement(queryInsert);   
	            
	            // 물음표가 4개 이므로 4개 각각 입력해줘야한다.
	            ReceivedDBScore = ReceivedDBScore +cardPuzzleScore   ;
	            pstmt.setInt(1, ReceivedDBScore); 				 // pstmt객체에 각각 셋팅
	            pstmt.setString(2, userId); 
	            pstmt.executeUpdate();
	            
	            //pstmt.executeUpdate(); create insert update delete 
	            //pstmt.executeQuery(); select 
	        }catch(Exception e2){
	            System.out.println(e2.getMessage());
	        }finally{
	            try {
	                pstmt.close();
	                   con.close();               // 역순으로 닫아준다.
	            } catch (Exception e2) {}
	        }
		}
	}
	//  EventHandlerResultOK
	
	
	public void mixNumber(){			// 카드 섞기
		
		int i=0;
		int rand=0;
		while(true){
			
			rand =  (int)(Math.random()*20+1);
			cardrandom[i] =rand;

	  aa : for(int j=0; j<20; j++){			
				if(j==i)
					break aa ;
				if(cardrandom[j]==rand){
					i=i-1 ;
					}
			}i=i+1;
			if(i==-1)i=0;
			if(i==20)break;
		}
	
	}
	
	public void setImage(){				// 이미지객체에 그림 가져오기
		
		imageBack = new ImageIcon(path +"pic/card/0.jpg");
		for ( int i = 0; i<20; i++){
			 imageIcon[i]= new ImageIcon(path +"pic/card/"+cardrandom[i]+".jpg");
		}
	}

	public void setButtonFirstImage(){		// 버튼 설정 및 처음 가져온 그림 보여주기
		
		for ( int i = 0; i<20; i++){
			 labelImage[i] =  new JLabel(imageIcon[i]);
			 labelImage[i].setBackground(Color.green); 	// 배경색
	
		}	
}

	public void setButtonResetImage(){		// 버튼에 리셋된 이미지 재설정하기

		for ( int i = 0; i<20; i++){
			 labelImage[i].setIcon(imageIcon[i]);
		}
	}
	
	public void setButtonName(){		// 버튼에 이름주기
		
		for ( int i = 0; i<20; i++){
			 labelImage[i].setName(Integer.toString(i+1));
		}		
	}

	public void hideButtonImage(){		// 버튼 그림 뒷면 보이기
		
		for ( int i = 0; i<20; i++){
			 labelImage[i].setIcon(imageBack);
		}	
	}
	
	
	void dialogResult(){		// 다이얼로그 보여주기
		
			labelResultText.setText("축하합니다 !!");
			labelResultScore.setText("점수 :  "+cardPuzzleScore+" 점 획득");
			dialogResult.setVisible(true);

	}
	
	
	public static void main(String[] args) {

		new WDB();
		
	}
	
}
