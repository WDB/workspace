import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.AbstractTableModel;

class myframe extends JFrame{
	JTextField t1title;
	JTextField t2pub;
	JTextField t3year;
	JTextField t4price;
	
	public myframe(){
		setSize(190,250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("�ؽ�Ʈ");
		
		JPanel panel = new JPanel();
		t1title = new JTextField(10);
		t1title.setText("��ü����");
		t2pub = new JTextField(10);
		t2pub.setText("�������ǻ�");
		t3year = new JTextField(10);
		t3year.setText("2016");
		t4price = new JTextField(10);
		t4price.setText("20000");
		panel.add(new JLabel("����"));
		panel.add(t1title);
		panel.add(new JLabel("���ǻ�"));
		panel.add(t2pub);
		panel.add(new JLabel("����"));
		panel.add(t3year);
		panel.add(new JLabel("����"));
		panel.add(t4price);
		
		JButton button = new JButton("���");
		panel.add(button);
		
		JButton button1 = new JButton("�������");
		panel.add(button1);
		
		mylistener mylistener = new mylistener(this);
		button.addActionListener(mylistener);
		
		yourlistener yourlistener = new yourlistener(this);
		button1.addActionListener(yourlistener);
		add(panel);
		setVisible(true);
	}
}
class jtablemodel extends AbstractTableModel{
	Object[][] data = {};
	
	String[]name = 	{"book_id","title","publisher","year","price"};
	
	public void setdata(String[][]input){
		data = input;
	}
	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return name.length;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.length;
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return data[arg0][arg1];
	}
	
}
class mylistener implements ActionListener{
	private myframe members;
	public mylistener(myframe d){
		members = d;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@192.168.0.22:1521:orcl";
			String id = "hrm";
			String pass = "hrm";
			Connection conn = DriverManager.getConnection(url,id,pass);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select max(book_id) from books");
			rs.next();
			int value = rs.getInt(1);
			
			value = value + 1;
			String sql = "insert into books(book_id,title,publisher,year,price)"
					+ " values(?,?,?,?,?)";
			PreparedStatement psmt = conn.prepareStatement(sql);
			
			psmt.setInt(1, value);
			psmt.setString(2, members.t1title.getText());
			psmt.setString(3, members.t2pub.getText());
			psmt.setString(4, members.t3year.getText());
			psmt.setString(5, members.t4price.getText());
			
			psmt.executeUpdate();
			psmt.close();
			conn.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
//select�� ����� ���ô�.
class yourlistener implements ActionListener{
	private myframe membermyframe;
	
	public yourlistener(myframe d){
		membermyframe = d;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String[][]source = null;
		System.out.println("��������ưŬ��");
		JFrame yourframe = new JFrame("�������");
		yourframe.setSize(300, 200);
		yourframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@192.168.0.22:1521:orcl";
			String id = "hrm";
			String pass = "hrm";
			Connection  conn = DriverManager.getConnection(url,id,pass);
			String string2 = "select count(*) from books";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(string2);
			rs.next();
			int rownum = rs.getInt(1);
			
			String string1 = "select * from books";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(string1);
			
			source = new String[rownum][5];
			int i  = 0, j = 0;
			while(rs.next()){
				source[i][0] = rs.getInt("book_id")+"";
				source[i][1] = rs.getString("title");
				source[i][2] = rs.getString("publisher");
				source[i][3] = rs.getString("year");
				source[i][4] = rs.getString("price")+" ";
				i++;
			}
			stmt.close();
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jtablemodel model = new jtablemodel();
		model.setdata(source);
		JTable table = new JTable(model);
		
		yourframe.add(new JScrollPane(table));
		model.setValueAt("�ٲﰪ", 0, 0);
		table.invalidate();
		yourframe.setVisible(true);
	}
	
}
public class WDB{
	public static void main(String args[]){
		new myframe();
	}
}