

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

	File path = new File("");   // ���� ��� ã��
	String userId ;			// �������� ���̵� �޾ƿ���
	
	int cardrandom[] = new int [20];		// ī�� ������ȣ�� �� �迭

	
	// �г� 1 �޴��κ�
	JPanel panel1;		
	int score=0;
	JLabel labelScore;			// �г�1�� ����
	int sec=0;
	JLabel labelTimer; 		// �ð��� ������ �г�1�� ��
	Timer timerSec = new Timer();		// �г�1�� ������ ���� Ÿ�̸� �ʱ�ȭ
	JButton buttonStart;	  		// ���� ���� ��ư

	
	// �г� 2 ���ӱ����κ�
	JPanel panel2;		
	ImageIcon imageBack ;			// �޸� �̹��� ������ ������
	ImageIcon imageIcon[]=new ImageIcon[20] ;			// �ո� �̹��� ������ 20�� �����ܵ�
	int startCheck;		// �����ӹ�ư �ȴ����� �׸� ������쿣 0, ������ ������ �д����� 1
	JLabel labelImage[]=new JLabel[20] ;		// �̹������� �÷��� ��ư 20��
	Timer timerMix = new Timer();		// �и� ������ �ӵ� �������� Ÿ�̸� �ʱ�ȭ
	Timer timerHide = new Timer();		// �и� ���������� �и� ���ʰ� �������� Ÿ�̸�
	Timer timerCardCheck = new Timer();		// �� 2���� �������� Ʋ���� ��� ���ʰ� �������� Ÿ�̸�
	JLabel labelConfirmedCheck;   	// 2�� �� ã�⸦ �����Ѱ��� �������� Ȯ���ϱ����ѹ�ư
	JLabel labelSelectedFirst;		// ó�� ���õ� ��ư
	JLabel labelSelectedSecond;		// �ι�° ���õ� ��ư
	int selectedTwoCardCheck;		// ��ư�� ��� ���Ǵ��� üũ�ϱ�
	int firstCardNumber;		// ù��° ���� ��ư���� �迭�� ī���ȣ �����ϱ�����
	int secondCardNumber;	// �ι�° ���� ��ư���� �迭�� ī���ȣ �����ϱ�����
	int selectedImageNumber ;		// ���° ��ư���� Ȯ���ϱ� ���� ������ ��ư�� ��ȣ ����
	ImageIcon selectedImage ;		// ������ �̹��� �����ֱ�����
	

	// ��� �˾�â
	JDialog dialogResult;
	JLabel labelResultText;
	JLabel labelResultScore;
	JButton buttonResultOK;
	int cardPuzzleScore=0;
	
	
	
	public WDB(){					// �ʱ� ������
	
		super("CardPuzzle");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel1 = new JPanel1();
		panel2 = new JPanel2();
		
		setBackground(Color.green);
		setLayout(new BorderLayout());
		startCheck=0;

		buttonResultOK = new JButton("Ȯ    ��");
		buttonResultOK.setFont(new Font("GOTHIC",Font.BOLD , 25));
		buttonResultOK.addActionListener(new EventHandlerResultOK());
		
		dialogResult = new JDialog(this, "���� ���");
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
	
	
	class JPanel1 extends JPanel{		// ��ư, ���г�
		
		public JPanel1(){
			
				setBackground(Color.green);
	            setLayout(new GridLayout(1,3,30,5));   // �г� ���̾ƿ� ����
	        
	            buttonStart =new JButton("New Game!!");

	        	labelTimer = new JLabel("  "+sec+" ��");	
	            labelTimer.setFont(new Font("GOTHIC",Font.BOLD , 20));
	    		labelTimer.setHorizontalAlignment(NORMAL);
	    		
	            labelScore = new JLabel("���� :  "+score + "�� ");
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
	// �г�2	
	
	class JPanel2 extends JPanel{		// ���� ���� �г�
		
		public JPanel2(){
		
			setBackground(Color.green);
			setLayout(new GridLayout(4,5,10,30));
			
			mixNumber();		// �м���
			setImage();			// �̹��� �޾ƿ���
			setButtonFirstImage();		// ��ư�� �̹��� �����
			setButtonName();			// ��ư�� ���� �̸� ���̱�
			
			for(int i =0; i<20;i++){
				labelImage[i].addMouseListener(new jjacMouseListener());
			}
			
			for(int i =0; i<20;i++){
				add(labelImage[i]);
			}
		}
	}
	// �г�2
	
	

	class MyActionListenerNewGame implements ActionListener {    //  ���� ���۹�ư
        @Override
        public void actionPerformed(ActionEvent e) {
        	
        	setButtonName(); 
        	startCheck=1; 
        	sec = 0;
        	score = 0;
    		labelTimer.setText("  "+sec+" ��");			// �� 0���� �ʱ�ȭ
    		labelScore.setText("���� :  "+score + "�� ");			// �� 0���� �ʱ�ȭ
    		
        	timerMix.cancel();	
    	  	timerMix = new Timer();		// Ÿ�̸� ��ü �ʱ�ȭ
    	  	timerHide.cancel();	
    		timerHide = new Timer();		// Ÿ�̸� ��ü �ʱ�ȭ
          	timerSec.cancel();				// Ÿ�̸� ��ü ���ֱ�
    	  	timerSec = new Timer();    // Ÿ�̸� ��ü �ʱ�ȭ
    	  	
        	timerMix.scheduleAtFixedRate(new TimerTask() { 		// �� ���� ���
        		int i=0;
				public void run() {
					mixNumber();
					setImage();
					setButtonResetImage();
					i=i+1;
					if(i==20)timerMix.cancel();			// 20�� �������� Ÿ�̸� �����Ű��
				}
			}, 0,50);			// 0���� �����ؼ� 0.005�ʰ������� ����
 			
        	
    		timerHide.scheduleAtFixedRate(new TimerTask() {		// �� 4�� ������ �� ������ ���
    			public void run() {
    				hideButtonImage();
    				timerHide.cancel();
    			}
    		}, 2000, 1); 		// 2���� ����
        	

        	timerSec.scheduleAtFixedRate(new TimerTask() { 		//  ���ӽ����� �ð���
        
				public  void run() {
					labelTimer.setText("  "+sec+" ��");	
					sec=sec+1;
				}
			}, 2000,1000);		// 2���� 1�� �������� ����
        
        }
     }
	// ���� ���۹�ư
 	
	private class jjacMouseListener implements MouseListener{		// �ΰ� ī�� Ȯ�� �� ��� ǥ��
		public void mouseClicked(java.awt.event.MouseEvent e) {		
		}
		public void mouseEntered(java.awt.event.MouseEvent e) {
		}
		public void mouseExited(java.awt.event.MouseEvent e) {	
		}
		public void mousePressed(java.awt.event.MouseEvent e) {		
			labelConfirmedCheck = ((JLabel)e.getSource());
        	if(labelConfirmedCheck.getName()=="checked"){    // ���� ��ư�� checked�̸� �����ѹ�ư �����Ÿ� ������ϱ�
            	selectedTwoCardCheck =0;    // ������ �и� �������Ƿ� ī��Ʈ �ٽ� 0����
            	
        	}else if(startCheck==1 && (selectedTwoCardCheck==0 || selectedTwoCardCheck==1)){  
        		// ī�尡 ������ �������� �����ϱ�����

        			selectedTwoCardCheck += 1;		 // ī�� ���� �ɶ����� ī��Ʈ + 1
        	
        			// ù��° ���� ī�� ��ȣ
        			if(selectedTwoCardCheck==1){		
        				
      	
        				labelSelectedFirst=((JLabel)e.getSource());  // ù��° ���� ��ư��ü ��������
        				selectedImageNumber =Integer.parseInt(labelSelectedFirst.getName()) -1;  // �̸��� string �̹Ƿ� int�� ��ȯ
        				selectedImage = new ImageIcon(path +"pic/card/"+cardrandom[selectedImageNumber]+".jpg"); 
        				labelSelectedFirst.setIcon(selectedImage);     // ��ư ������ �̹��� �����ֱ�
        		 
        				firstCardNumber = cardrandom[selectedImageNumber];	// ī�� ��ȣ�� 10���ϸ� �׳� ����
        				if(cardrandom[selectedImageNumber]>10)			// ī�� ��ȣ�� 10���� ũ�� -10
        					firstCardNumber = cardrandom[selectedImageNumber]-10; 
        			}// ù��° ���� ī�� ��ȣ

        			// �ι�° ���� ī�� ��ȣ
    				if(labelConfirmedCheck.getName()==labelSelectedFirst.getName()){		// �ι�° Ŭ���� ó��Ŭ���� ī�带 �� ���������� ī��Ʈ0
    					selectedTwoCardCheck =1;
    				}else if(selectedTwoCardCheck==2	){			
        				labelSelectedSecond=((JLabel)e.getSource());  // ù��° ���� ��ư��ü ��������
        				selectedImageNumber =Integer.parseInt(labelSelectedSecond.getName()) -1;  // �̸��� string �̹Ƿ� int�� ��ȯ
        				selectedImage = new ImageIcon(path +"pic/card/"+cardrandom[selectedImageNumber]+".jpg"); 
        				labelSelectedSecond.setIcon(selectedImage);     // ��ư ������ �̹��� �����ֱ�
        				
        				secondCardNumber = cardrandom[selectedImageNumber];	// ī�� ��ȣ�� 10���ϸ� �׳� ����
        				if(cardrandom[selectedImageNumber]>10)				// ī�� ��ȣ�� 10���� ũ�� -10
        					secondCardNumber = cardrandom[selectedImageNumber]-10; 
        				
        				if(firstCardNumber==secondCardNumber ){   	// ù��° �ι�° ���õ� ī�� ��
        					selectedTwoCardCheck =0;		// ī�� 2���õǸ� �ٽ�  �ʱ�ȭ
        					labelSelectedFirst.setName("checked"); 				// �����̹Ƿ� ��ư �̸� 50���� �ؼ� ���ø��ϰ� �ϱ�
        					labelSelectedSecond.setName("checked"); 			// �����̹Ƿ� ��ư �̸� 50���� �ؼ� ���ø��ϰ� �ϱ�
        			 
        					int end=0;  // �� � ������� Ȯ���ϱ� 
        					for(int i=0; i<20; i++){		 // �� � ������� Ȯ���ϱ� 
        						if((labelImage[i].getName()).equals("checked")){
        							end=end+1;
        							if (end==20){		// 20�� ���� �¾Ҵ��� üũ
        								
        								timerSec.cancel();		//  Ÿ�̸� ����
        								labelTimer.setText("  "+sec+" ��");	
        								if(sec>100){  // 100�� �ѵ��� �� Ǯ�� 100�ʶ�� ���� ������ ��� 0��
        									sec=100;
        								}
        								cardPuzzleScore = 100-sec;
        								labelScore.setText("���� :  "+cardPuzzleScore+" ��");
        								
        								dialogResult();
        								break;
        							}
        						}
        					}
        					
        				}else{
        					timerCardCheck = new Timer();
        					timerCardCheck.scheduleAtFixedRate(new TimerTask() { 	//ù��° �ι�° ī����� Ʋ���� ���ʺ����ֱⰣ 
        	
        						public void run() {
        							selectedTwoCardCheck =0;		// ī�� 2���õǸ� �ٽ�  �ʱ�ȭ , ī�尡 �޸麸�϶����� �ٸ��� ���ø��ϰ��ϱ�
        							labelSelectedFirst.setIcon(imageBack); 				// Ʋ�����Ƿ� ù��° ī�� �ٽ� ������
        							labelSelectedSecond.setIcon(imageBack); 			// Ʋ�����Ƿ� �ι�° ī�� �ٽ� ������
        							timerCardCheck.cancel();				// ������ Ÿ�̸� ����
        						}
        					}, 300,1);				// 0.4���� ī�� �޸� ���̰� �ϱ�.
        				} 
        			}// �ι�° ���� ī�� ��ȣ		
        	}
			
		}
		public void mouseReleased(java.awt.event.MouseEvent e) {
		}
	}
	// �ΰ� ī�� Ȯ�� �� ��� ǥ��

	class EventHandlerResultOK implements ActionListener{    // ���Ӱ��Ȯ�ι�ư���� DB�� ���� �����ϱ�  // ���� �ּ�ó���ص��ȴ�.
		
		public void actionPerformed(ActionEvent e){	
			
			dialogResult.dispose();   // Ȯ�ι�ư �ִ� ���̾�α�â ���ֱ�

			int ReceivedDBScore=0;
			
			String driver = "oracle.jdbc.driver.OracleDriver";
	        String url = "jdbc:oracle:thin:@111.222.253.124:1521:XE";   // �ڽ��� ����Ŭ �����Ƿ��ؾ��Ѵ�.

	        Connection con = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;   // ���Ϲ޾� ����� ��ü ����
	     
   	        String querySelect = "select id, jjackscore from USER_INFO where id=?"; 	 
										// SUTDASCORE �˻�   // �ڽ��� ����ŬDB�� �°� �����ؾ��Ѵ�.
	        
	        try{													// select
	            Class.forName(driver);
	            con = DriverManager.getConnection(url, "aaa", "aaa");
	            pstmt = con.prepareStatement(querySelect);
	           
	            pstmt.setString(1, userId); 
	            pstmt.executeUpdate();		// ������ ����
	            
	            rs = pstmt.executeQuery(); // ���Ϲ޾ƿ� ���� ������ ��ü����
	            while(rs.next()){
	                ReceivedDBScore = rs.getInt("jjackscore");
	            }
	            
	        }catch(Exception e1){
	            System.out.println(e1.getMessage());
	        }finally{
	            try {
	                rs.close(); 
	                pstmt.close(); 
	                con.close();   // ��ü ������ �ݴ� ������ ����� ��ü�� �ݾ��ش�.
	            } catch (Exception e2) {}
	        }

	        
	        String queryInsert = "UPDATE user_info SET jjackscore=? where id=?";
	        try{										// insert
	        	//  Class.forName(driver);  // ����̹� �ε�
	            con = DriverManager.getConnection(url, "aaa", "aaa"); // DB ����   // �ڽ��� ����ŬDB�� �°� �����ؾ��Ѵ�.
	            pstmt = con.prepareStatement(queryInsert);   
	            
	            // ����ǥ�� 4�� �̹Ƿ� 4�� ���� �Է�������Ѵ�.
	            ReceivedDBScore = ReceivedDBScore +cardPuzzleScore   ;
	            pstmt.setInt(1, ReceivedDBScore); 				 // pstmt��ü�� ���� ����
	            pstmt.setString(2, userId); 
	            pstmt.executeUpdate();
	            
	            //pstmt.executeUpdate(); create insert update delete 
	            //pstmt.executeQuery(); select 
	        }catch(Exception e2){
	            System.out.println(e2.getMessage());
	        }finally{
	            try {
	                pstmt.close();
	                   con.close();               // �������� �ݾ��ش�.
	            } catch (Exception e2) {}
	        }
		}
	}
	//  EventHandlerResultOK
	
	
	public void mixNumber(){			// ī�� ����
		
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
	
	public void setImage(){				// �̹�����ü�� �׸� ��������
		
		imageBack = new ImageIcon(path +"pic/card/0.jpg");
		for ( int i = 0; i<20; i++){
			 imageIcon[i]= new ImageIcon(path +"pic/card/"+cardrandom[i]+".jpg");
		}
	}

	public void setButtonFirstImage(){		// ��ư ���� �� ó�� ������ �׸� �����ֱ�
		
		for ( int i = 0; i<20; i++){
			 labelImage[i] =  new JLabel(imageIcon[i]);
			 labelImage[i].setBackground(Color.green); 	// ����
	
		}	
}

	public void setButtonResetImage(){		// ��ư�� ���µ� �̹��� �缳���ϱ�

		for ( int i = 0; i<20; i++){
			 labelImage[i].setIcon(imageIcon[i]);
		}
	}
	
	public void setButtonName(){		// ��ư�� �̸��ֱ�
		
		for ( int i = 0; i<20; i++){
			 labelImage[i].setName(Integer.toString(i+1));
		}		
	}

	public void hideButtonImage(){		// ��ư �׸� �޸� ���̱�
		
		for ( int i = 0; i<20; i++){
			 labelImage[i].setIcon(imageBack);
		}	
	}
	
	
	void dialogResult(){		// ���̾�α� �����ֱ�
		
			labelResultText.setText("�����մϴ� !!");
			labelResultScore.setText("���� :  "+cardPuzzleScore+" �� ȹ��");
			dialogResult.setVisible(true);

	}
	
	
	public static void main(String[] args) {

		new WDB();
		
	}
	
}
