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
	
	//����������ſռ������
	private JPanel jp=new JPanel();
	
	private JLabel jl1=new JLabel("��    ��    ��");
	private JLabel jl2=new JLabel("��            ��");
	
	//���ڵ�½��ʾ��ǩ
	private JLabel jl3=new JLabel("");
	
	private JTextField jtf=new JTextField();
	private JPasswordField jpwf=new JPasswordField();
	private JRadioButton[] jrbArray=
	        {
	        	new JRadioButton("��ͨѧ��",true),
	        	new JRadioButton("������Ա")
	        };

	private ButtonGroup bg=new ButtonGroup();
	private JButton jb1=new JButton("��    ½");
	private JButton jb2=new JButton("��    ��");

	public F_Login()
	{ 
	    this.addListener();
		initialFrame();
	}
	
	public void addListener(){
		this.jb1.addActionListener(this);//Ϊ��½��ťע�������
		this.jb2.addActionListener(this);//Ϊ���ð�ťע�������
		this.jtf.addActionListener(this);//Ϊ�û����ı���ע�������
		this.jpwf.addActionListener(this);//Ϊ�û��������ע�������
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

		this.setTitle("��½");
		Image image=new ImageIcon("ico.gif").getImage();  
 		this.setIconImage(image);
		this.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=300;//��������
		int h=220;//������߶�
		this.setBounds(centerX-w/2,centerY-h/2-100,w,h);//���ô����������Ļ����
		this.setVisible(true);
		this.jtf.requestFocus(true);

	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==this.jb1)
		{

			String name=this.jtf.getText().trim();
			
			if(name.equals("")){
				JOptionPane.showMessageDialog(this,"�������û���","����",
				                               JOptionPane.ERROR_MESSAGE);
				jl3.setText("");return;
			}
			
			String pwd=this.jpwf.getText().trim();
			if(pwd.equals("")){
				JOptionPane.showMessageDialog(this,"����������","����",
				                           JOptionPane.ERROR_MESSAGE);
				jl3.setText("");return;
			}
			
			int type=this.jrbArray[0].isSelected()?0:1;//��ȡ��½����
			
			try{   
	            this.initialConnection();
	            
				if(type==0){//��ͨѧ����½
					String sql="select * from user_stu where "+
					"s_id='"+name+"' and pwd='"+pwd+"'";
					rs=stmt.executeQuery(sql);
					if(rs.next()){
						new S_StuClient(name,host);//����ѧ���ͻ��̴���
						this.dispose();//�رյ�½���ڲ��ͷ���Դ
					}
					else{
						JOptionPane.showMessageDialog(this,"�û������������","����",
						                           JOptionPane.ERROR_MESSAGE);
						jl3.setText("");
					}
					this.closeConn();
				}
				
				else{//��ʦ��½
					
					String sql="select coll_id from user_teacher where "+
					             "t_id='"+name+"' and pwd='"+pwd+"'";
					rs=stmt.executeQuery(sql);
					
					if(rs.next()){
						String coll_id=rs.getString(1);
						new T_TeacherClient(coll_id,host);
						this.dispose();
					}
					
					else{
						JOptionPane.showMessageDialog(this,"�û������������","����",
						                           JOptionPane.ERROR_MESSAGE);
						jl3.setText("");
					}
					
					this.closeConn();	
				}
			}
			catch(SQLException ea){ea.printStackTrace();}
		}
		else if(e.getSource()==this.jb2){//�������ð�ť,���������Ϣ
			this.jtf.setText("");
			this.jpwf.setText("");
		}
		else if(e.getSource()==jtf){//�������û������س�ʱ
			this.jpwf.requestFocus(true);
		}
		else if(e.getSource()==jpwf){//���������벢�س�ʱ	
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
			JOptionPane.showMessageDialog(this,"����ʧ�ܣ�����������ַ�Ƿ���ȷ","����",JOptionPane.ERROR_MESSAGE);
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