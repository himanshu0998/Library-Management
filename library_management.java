import java.util.*;
import java.io.*;
import java.sql.*;
import java.util.concurrent.*;

import com.mysql.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

interface OnCallBack
{
	void onCallBack(String s);
}

public class library_management
{
	public static Map<String,String> result = new HashMap<>();
	public static String Address,Subject,Message;
	public static void main(String [] args)
	{
		Subject = "Book is Available";
		Message = "The book you had requested for is available now";
		JFrame frame = new JFrame("LIBRARY");
		frame.add( new server(args));
		//frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    //to close when exit button is pressed
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);			//set screen with given size
		frame.setVisible(true);  
	}
	
	
}

class server extends JComponent
implements MouseListener,ActionListener
{
	String [] args;
	user<Integer> uid1 = null;
	user<String> uid2 = null;
	library<Double> obj1 = null;
	Student student = null;
	Books books = null;
	Fine fine = null;
	JsoupGoogleSearch search = null;
	HyperlinkDemo link = null;
	IssueRequest request = null;
	SwingEmailSender ses = null;
	OnCallBack ocb = null;
	ThreadPool tp = null;
	JButton newUser,addInfo,buy,returnBook,Trends,yourBooks,issueRequest;
	JPanel panel,panel1,panel2,panel3,panel4,panel5,panel6,panel7,panel8,panel9,panel10,panel11,panel13,img_pan;
	JTable table;
	Connection con = null;
	JScrollPane scrollPane;
	public server(String [] args)
	{
		this.args = args;
		try
		{
			panel = new JPanel();
			panel.setBounds(0,0,250,1000);
			panel.setBackground(Color.BLACK);
			panel1 = new JPanel();
			panel1.setBounds(0, 0, 250, 50);
			panel1.setBackground(new Color(165,70,30));
			panel2 = new JPanel();
			panel2.setBounds(250, 0, 1440, 5);
			panel2.setBackground(Color.BLACK);
			panel3 = new JPanel();
			panel3.setBounds(0, 60, 250, 50);
			panel3.setBackground(new Color(165,70,30));
			panel4 = new JPanel();
			panel4.setBounds(950,0,600,350);
			panel4.setBackground(Color.LIGHT_GRAY);
			panel5 = new JPanel();
			panel5.setBounds(0,120,250,50);
			panel5.setBackground(new Color(165,70,30));
			panel7 = new JPanel();
			panel7.setBounds(0,180,250,50);
			panel7.setBackground(new Color(165,70,30));
			panel9 = new JPanel();
			panel9.setBounds(0,240,250,50);
			panel9.setBackground(new Color(165,70,30));
			panel11 = new JPanel();
			panel11.setBounds(0,300,250,50);
			panel11.setBackground(new Color(165,70,30));
			panel13 = new JPanel();
			panel13.setBounds(0,360,250,50);
			panel13.setBackground(new Color(165,70,30));
			img_pan = new JPanel();
			img_pan.setBounds(0,0,1920,1080);
			newUser = new JButton("New User");
			//newUser.setBounds(0, 0, 100, 30);
			addInfo = new JButton("Add Information");
			//addInfo.setBounds(0,60,200,30);
			buy = new JButton("Issue a Book");
			//buy.setBounds(0,100,100,30);
			returnBook = new JButton("Return a book");
			//returnBook.setBounds(0, 400, 100, 30);
			Trends = new JButton("Current Trends");
			//Trends.setBounds(0,);
			yourBooks = new JButton("Your Books");
			issueRequest = new JButton("Issue Request");
			panel1.add(newUser);
			panel3.add(addInfo);
			panel5.add(buy);
			panel7.add(returnBook);
			panel9.add(Trends);
			panel11.add(yourBooks);
			panel13.add(issueRequest);
			add(panel1);  
			add(panel2);
			add(panel3);
			add(panel4);
			//add(panel10);
			add(panel5);
			add(panel7);
			add(panel9);
			add(panel11);
			add(panel13);
			add(panel);
			panel4.setVisible(false);
			newUser.addActionListener(this);
			addInfo.addActionListener(this);
			buy.addActionListener(this);
			returnBook.addActionListener(this);
			Trends.addActionListener(this);
			yourBooks.addActionListener(this);
			issueRequest.addActionListener(this);
			addMouseListener(this);
			
			try {
				BufferedImage myPicture = ImageIO.read(new File("resized_background.jpg"));
				JLabel picLabel = new JLabel(new ImageIcon(myPicture));
				picLabel.setBounds(0, 0, 1440, 1080);
				img_pan.add(picLabel);
			}
			catch(Exception E) {
				E.printStackTrace();
			}
			add(img_pan);
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/assignments","root","password");
			obj1 = new library<Double>(con);
			//Scanner in = new Scanner(System.in);
			uid1 = new user<Integer>(con);
			uid2 = new user<String>(con);
		    ocb = new thread();
			tp = new ThreadPool(ocb);
			student = new Student(con);
			books = new Books(con);
			fine = new Fine(con);
			search = new JsoupGoogleSearch();
			request = new IssueRequest(con);
			ses = new SwingEmailSender();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == newUser)
		{
			String n = JOptionPane.showInputDialog(null, "User Name : ");
			String t = JOptionPane.showInputDialog(null, "Id type?\n1.Integer\n2.String\n\n");
			if(t.equals("Integer"))
			{
				String t1 = JOptionPane.showInputDialog(null,"Id : ");
				Integer id = Integer.parseInt(t1);
				try 
				{
					uid1.new_user(n, id, tp, t);
				}
				catch (SQLException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(t.equals("String"))
			{
				String t1 = JOptionPane.showInputDialog(null,"Id : ");
				try 
				{
					uid2.new_user(n, t1, tp, t);
				}
				catch (SQLException e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Invalid Input");
			}
		}
		else if(e.getSource() == buy)
		{
			int count = 0;
			try 
			{
				ResultSet cnt = this.obj1.display(con);
				while(cnt.next())
				{
					count++;
				}
			} 
			catch (SQLException e2) 
			{
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			
			//JFrame tFrame = new JFrame("BOOKS");
			String column[] = {"Name","Author","Price","Stock"};
			String data[][] = new String[count][4];
			int i = 0;
			try 
			{
				ResultSet rs = this.obj1.display(this.con);
				while(rs.next())
				{
					data[i][0] = rs.getString(1);
					data[i][1] = rs.getString(2);
					data[i][2] = rs.getString(3);
					data[i][3] = rs.getString(4);
					i++;
				}
			} 
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
			JTable table = new JTable(data,column);
			table.setBounds(30,40,700,1000);
			table.setBackground(Color.LIGHT_GRAY);
			JScrollPane sp = new JScrollPane(table);
			sp.setBounds(850, 0, 400, 1000);
			//tFrame.getContentPane().add(sp);
			//tFrame.setSize(700, 700);
			//tFrame.setVisible(true);
			//tFrame.setLocation(800, 40);
			//tFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			panel4.add(sp);
			panel4.setVisible(true);
			String name = JOptionPane.showInputDialog(null,"Enter Book name : ");
			String stud_id = JOptionPane.showInputDialog(null, "Enter student id : ");
			String stud_name = JOptionPane.showInputDialog(null, "Enter student name : ");
			try
			{
				if(student.insert(stud_id, stud_name, con))
				{
					String ans = this.obj1.buy(name, this.con);
					JOptionPane.showMessageDialog(null,ans);
					String check = "Book not available";
					if(ans.equalsIgnoreCase(check))
					{
						student.delete(stud_id, con);
					}
					else
					{
						System.out.println(1);
						String msg = books.insert(name, stud_id, con);
						String msg2 = "The return date is : "+msg;
						JOptionPane.showMessageDialog(null, msg2);
					}
				}
				else
				{
					String ans = "You have maximum number of books issued, cannot issue another";
					JOptionPane.showMessageDialog(null,ans);
				}
			}
			catch(Exception ex)
			{
				
			}
			panel4.remove(sp);
			panel4.setVisible(false);
		}
		else if(e.getSource()==addInfo)
		{
			try
			{
				String n = JOptionPane.showInputDialog(null, "User Name : ");
				String t = JOptionPane.showInputDialog(null, "Id type?\n1.Integer\n2.String\n\n");
				if(t.equals("Integer"))
				{
					String t1 = JOptionPane.showInputDialog(null,"Id : ");
					Integer id = Integer.parseInt(t1);
					if(uid1.exists(n,id,t))
					{
						String b = JOptionPane.showInputDialog(null, "Enter book Name : ");
						String a = JOptionPane.showInputDialog(null, "Enter author : ");
						String n1 = JOptionPane.showInputDialog(null, "Enter price : ");
						String n2 = JOptionPane.showInputDialog(null, "Enter stock : ");
						String c = JOptionPane.showInputDialog(null, "Enter category : ");
						Integer p=0;
						Double s=0.0;
						if(n2!=null)
						{
							p = Integer.parseInt(n2);
						}
						if(n1!=null)
						{
							s = Double.parseDouble(n1);
						}
						obj1.accept(b,a,s,p,c);
					}
				}
				else if(t.equals("String"))
				{
					String t1 = JOptionPane.showInputDialog(null,"Id : ");
					if(uid2.exists(n,t1,t))
					{
						String b = JOptionPane.showInputDialog(null, "Enter book Name : ");
						String a = JOptionPane.showInputDialog(null, "Enter author : ");
						String n1 = JOptionPane.showInputDialog(null, "Enter price : ");
						String n2 = JOptionPane.showInputDialog(null, "Enter stock : ");
						String c = JOptionPane.showInputDialog(null, "Enter category : ");
						Integer p=0;
						Double s=0.0;
						if(n2!=null)
						{
							p = Integer.parseInt(n2);
						}
						if(n1!=null)
						{
							s = Double.parseDouble(n1);
						}
						obj1.accept(b,a,s,p,c);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Invalid Input");
				}
			}
			catch(SQLException s1)
			{
				s1.printStackTrace();
			}
		}
		else if(e.getSource() == returnBook)
		{
			String id = JOptionPane.showInputDialog(null,"Enter your id : ");
			String b = JOptionPane.showInputDialog(null,"Enter Book name : ");
			try 
			{
				
				Statement stmt = con.createStatement();
//				String s = "select category from library where name = '"+b+"';";
//				ResultSet rs = stmt.executeQuery(s);
//				try 
//				{
//					if(rs.next())
//					{
//						library_management.result = search.getLinks(rs.getString(1));
//						library_management.category = rs.getString(1);
//					}
//				}
//				catch (IOException e1)
//				{
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				Double f = fine.fine(id, b, con);
				if(f!=0)
				{
					String s1 = "You have crossed the return date. Please pay fine of "+f+" rupees";
					JOptionPane.showMessageDialog(null, s1);
				}
				JOptionPane.showMessageDialog(null, "Book returned successfully");
				obj1.update(b);
				books.delete(b, id, con);
				request.sendEmail(b, con);
				if(library_management.Address.length()!=0)
				{
					ses.send();
				}
				library_management.Address = "";
//				link = new HyperlinkDemo();
//				link.launchStage(args);
			}
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}	
		}
		else if(e.getSource() == Trends)
		{
			String s = JOptionPane.showInputDialog(null,"Enter category : ");
			try 
			{
				library_management.result = search.getLinks(s);
			}
			catch (IOException e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			HyperlinkDemo link = new HyperlinkDemo();
			link.launchStage(args);
		}
		else if(e.getSource() == yourBooks) 
		{
			String id = JOptionPane.showInputDialog(null,"Enter id : ");
			try
			{
				Statement stmt = con.createStatement();
				String s = "select * from books where stud_id = '"+id+"';";
				ResultSet rs = stmt.executeQuery(s);
				String ans = "";
				while(rs.next())
				{
					ans = ans + "Book : "+rs.getString(1)+" return date : "+rs.getString(3)+"\n";
				}
				if(ans.length()!=0)
				{
					JOptionPane.showMessageDialog(null, ans);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "No Books Issued");
				}
			}
			catch (SQLException e1) 
			{
				e1.printStackTrace();
			}
		}
		else if(e.getSource() == issueRequest)
		{
			String id = JOptionPane.showInputDialog(null,"Enter id : ");
			String b = JOptionPane.showInputDialog(null,"Enter book name : ");
			try
			{
				request.issue(con, id, b);
			} 
			catch (SQLException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}

class library<T>
{
	Connection con = null;
	public library(Connection con) throws SQLException
	{
		this.con = con;
		Statement stmt = con.createStatement();
		stmt.executeUpdate("create table if not exists library(name varchar(20),author varchar(20),price float,stock int);");
	}
	public void accept(String s1, String s2, Double d, Integer i,String c) throws SQLException
	{
		// TODO Auto-generated method stub
		Statement pre = con.createStatement();
		String s3 = "select price,stock from library where name = '"+s1+"';";
		ResultSet rs = pre.executeQuery(s3);
		if(rs.next())
		{
			Statement stmt = con.createStatement();
			String s = "update library set stock = "+(i+rs.getInt(2))+" where name = '"+s1+"';";
			stmt.executeUpdate(s);
		}
		else
		{
			Statement stmt = con.createStatement();
			String s = "insert into library values('"+s1+"','"+s2+"',"+d+","+i+",'"+c+"');";
			stmt.executeUpdate(s);
		}
	}

	public String buy(String b,Connection con)throws SQLException
	{
		// TODO Auto-generated method stub
		Statement stmt = con.createStatement();
		String s1 = "select price,stock from library where name = '"+b+"';";
		ResultSet rs = stmt.executeQuery(s1);
		//System.out.println(1);
		if(rs.next())
		{
			double price = rs.getDouble(1);
			int copies = rs.getInt(2) - 1;
			String s2 = "update library set stock = "+copies+" where name = '"+b+"';";
			Statement stmt2 = con.createStatement();
			stmt2.executeUpdate(s2);
//			String s3 = "delete from library where stock = 0;";
//			Statement stmt3 = con.createStatement();
//			stmt3.executeUpdate(s3);
			return ("Book has been issued");
		}
		else
		{
			return ("Book not available");
		}
	}

	public ResultSet display(Connection con) throws SQLException
	{
		// TODO Auto-generated method stub
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select * from library;");
		return rs;
		/*int cnt = 0;
		while(rs.next())
		{
			System.out.println(rs.getString(1)+"\t"+rs.getString(2)+"\t"+rs.getDouble(3)+"\t"+rs.getInt(4));
			cnt++;
		}
		if(cnt==0)
			return false;
		return true;*/
	}
	
	public void update(String b)throws SQLException
	{
		Statement stmt1 = con.createStatement();
		String s = "update library set stock = stock + 1 where name = '"+b+"';";
		stmt1.executeUpdate(s);
	}
}
class user<T>
{
	Connection con = null;
	public user(Connection con) throws SQLException
	{
		 this.con = con;
		 Statement stmt = con.createStatement();
		 stmt.executeUpdate("Create table if not exists userInt(uname varchar(20),uid int);");
		 Statement stmt1 = con.createStatement();
		 stmt1.executeUpdate("Create table if not exists userStr(uname varchar(20),uid varchar(20));");
	}
	public void new_user(String s, T t,ThreadPool tp,String type) throws SQLException
	{
		if(type.equals("Integer"))
		{
			Statement pre = con.createStatement();
			String s3 = "select * from userInt where uname = '"+s+"';";
			ResultSet rs = pre.executeQuery(s3);
			if(rs.next())
			{
				JOptionPane.showMessageDialog(null,"Username already exists!!");
			}
			else
			{
				Statement stmt = con.createStatement();
				String s1 = "insert into userInt values('"+s+"', "+t+");";
				stmt.executeUpdate(s1);
			}
		}
		else
		{
			Statement pre = con.createStatement();
			String s3 = "select * from userStr where uname = '"+s+"';";
			ResultSet rs = pre.executeQuery(s3);
			if(rs.next())
			{
				JOptionPane.showMessageDialog(null,"Username already exists!!");
			}
			else
			{
				Statement stmt = con.createStatement();
				String s1 = "insert into userStr values('"+s+"', '"+t+"');";
				stmt.executeUpdate(s1);
			}
		}
	}
	public boolean exists(String s, T t, String type)throws SQLException
	{
		boolean ans = false;
		if(type.equals("Integer"))
		{
			Statement stmt = con.createStatement();
			String s1 = "select * from userInt where uname = '"+s+"';";
			ResultSet rs = stmt.executeQuery(s1);
			if(rs.next())
			{
				ans=true;
			}
		}
		else
		{
			Statement stmt = con.createStatement();
			String s1 = "select * from userStr where uname = '"+s+"';";
			ResultSet rs = stmt.executeQuery(s1);
			if(rs.next())
			{
				ans=true;
			}
		}
		return ans;
	}
}

class thread extends Thread implements OnCallBack
{
	
	public void run() {
		
		//System.out.println(Thread.currentThread().getName() + " added to the system");
	}

	public void onCallBack(String s) {
		// TODO Auto-generated method stub
		System.out.println("\n" + s + " added to the system");
	}
}
class ThreadPool
{
	private OnCallBack ocb;
	ExecutorService executor;
	public ThreadPool(OnCallBack ocb)
	{
		executor = Executors.newFixedThreadPool(2);
		this.ocb = ocb;
	}
	public void addToPool(String s)
	{
		Thread user = new thread();
		user.setName(s);
		executor.execute(user);
		ocb.onCallBack(s);
	}
	public void end()
	{
		executor.shutdown();
	}
}
