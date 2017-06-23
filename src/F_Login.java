import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.net.*;
import java.io.*;
import java.sql.*;
import javax.sql.*;
public class F_Login extends JFrame implements ActionListener
{
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	public String host="127.0.0.1:3306";
	
	//创建用来存放空间的容器
	private JPanel jp=new JPanel();
	
	private JLabel jl1=new JLabel("用    户    名");
	private JLabel jl2=new JLabel("密            码");
	
	//正在登陆提示标签
	private JLabel jl3=new JLabel("");
	
	private JTextField jtf=new JTextField();
	private JPasswordField jpwf=new JPasswordField();
	private JRadioButton[] jrbArray=
	        {
	        	new JRadioButton("普通学生",true),
	        	new JRadioButton("管理人员")
	        };

	private ButtonGroup bg=new ButtonGroup();
	private JButton jb1=new JButton("登    陆");
	private JButton jb2=new JButton("重    置");

	public F_Login()
	{ 
	    this.addListener();
		initialFrame();
	}
	
	public void addListener(){
		this.jb1.addActionListener(this);//为登陆按钮注册监听器
		this.jb2.addActionListener(this);//为重置按钮注册监听器
		this.jtf.addActionListener(this);//为用户名文本框注册监听器
		this.jpwf.addActionListener(this);//为用户名密码框注册监听器
	}
	public void initialFrame()
	{
		
		jp.setLayout(null);
		
		this.jl1.setBounds(30,20,110,25);
		this.jp.add(jl1);
		
		this.jtf.setBounds(120,20,130,25);
		this.jp.add(jtf);
		
		this.jl2.setBounds(30,60,110,25);
		this.jp.add(jl2);
		
		this.jpwf.setBounds(120,60,130,25);
		this.jpwf.setEchoChar('*');
		this.jp.add(jpwf);
		
		this.bg.add(jrbArray[0]);
		this.bg.add(jrbArray[1]);
		this.jrbArray[0].setBounds(40,100,100,25);
		this.jp.add(jrbArray[0]);
		this.jrbArray[1].setBounds(145,100,100,25);
		this.jp.add(jrbArray[1]);
		this.jb1.setBounds(35,130,100,30);
		this.jp.add(jb1);
		this.jb2.setBounds(150,130,100,30);
		this.jp.add(jb2);
		this.jl3.setBounds(40,170,150,25);
		this.jp.add(jl3);
		this.add(jp);

		this.setTitle("登陆");
		Image image=new ImageIcon("ico.gif").getImage();  
 		this.setIconImage(image);
		this.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=300;//本窗体宽度
		int h=220;//本窗体高度
		this.setBounds(centerX-w/2,centerY-h/2-100,w,h);//设置窗体出现在屏幕中央
		this.setVisible(true);
		this.jtf.requestFocus(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==this.jb1)
		{

			String name=this.jtf.getText().trim();
			
			if(name.equals("")){
				JOptionPane.showMessageDialog(this,"请输入用户名","错误",
				                               JOptionPane.ERROR_MESSAGE);
				jl3.setText("");return;
			}
			
			String pwd=this.jpwf.getText().trim();
			if(pwd.equals("")){
				JOptionPane.showMessageDialog(this,"请输入密码","错误",
				                           JOptionPane.ERROR_MESSAGE);
				jl3.setText("");return;
			}
			
			int type=this.jrbArray[0].isSelected()?0:1;//获取登陆类型
			
			try{   
	            this.initialConnection();
	            
				if(type==0){//普通学生登陆
					String sql="select * from user_stu where "+
					"s_id='"+name+"' and pwd='"+pwd+"'";
					rs=stmt.executeQuery(sql);
					if(rs.next()){
						new S_StuClient(name,host);//创建学生客户短窗口
						this.dispose();//关闭登陆窗口并释放资源
					}
					else{
						JOptionPane.showMessageDialog(this,"用户名或密码错误","错误",
						                           JOptionPane.ERROR_MESSAGE);
						jl3.setText("");
					}
					this.closeConn();
				}
				
				else{//教师登陆
					
					String sql="select coll_id from user_teacher where "+
					             "t_id='"+name+"' and pwd='"+pwd+"'";
					rs=stmt.executeQuery(sql);
					
					if(rs.next()){
						String coll_id=rs.getString(1);
						new T_TeacherClient(coll_id,host);
						this.dispose();
					}
					
					else{
						JOptionPane.showMessageDialog(this,"用户名或密码错误","错误",
						                           JOptionPane.ERROR_MESSAGE);
						jl3.setText("");
					}
					
					this.closeConn();	
				}
			}
			catch(SQLException ea){ea.printStackTrace();}
		}
		else if(e.getSource()==this.jb2){//按下重置按钮,清空输入信息
			this.jtf.setText("");
			this.jpwf.setText("");
		}
		else if(e.getSource()==jtf){//当输入用户名并回车时
			this.jpwf.requestFocus(true);
		}
		else if(e.getSource()==jpwf){//当输入密码并回车时	
			this.jb1.requestFocus(true);
		}
	}

	public void  initialConnection()
	{
		try
		{
			Class.forName("org.gjt.mm.mysql.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://"+host+"/test","root","2535663");
			stmt=conn.createStatement();
		}
		catch(SQLException e)
		{
			JOptionPane.showMessageDialog(this,"连接失败，请检查主机地址是否正确","错误",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public void closeConn()
	{
		try
		{
			if(rs!=null)
			{
				rs.close();
			}
			if(stmt!=null)
			{
				stmt.close();
			}
			if(conn!=null)
			{
				conn.close();
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void main(String args[])
	{

		    F_Login login=new F_Login();
	}
}