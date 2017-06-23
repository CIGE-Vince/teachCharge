import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import java.io.*;
import javax.swing.tree.*;
import java.sql.*;
import javax.sql.*;
public class T_NewClass extends JPanel implements ActionListener
{	
	private String host;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;

	private String coll_id;
	private Vector v_dept=new Vector();

	JLabel[] jlArray={new JLabel("专   业"),new JLabel("班   号"),
						new JLabel("班   名")
	};

	JComboBox jcb;

	JTextField jtf1=new JTextField();
	JTextField jtf2=new JTextField();
	JButton jb1=new JButton("提  交");
	JButton jb2=new JButton("重  置");

	public T_NewClass(String coll_id,String host)
	{
		this.host=host;
		this.coll_id=coll_id;
		this.initialData();
		this.initialFrame();
		this.addListener();
	}
	
	public void initialData()
	{
		try
		{//根据学院号获得该学院的专业名称列表
			this.initialConnection();
			String sql="select dept_name from dept where coll_id='"+coll_id+"'";
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				String dept_name=new String(rs.getString(1).getBytes("gb2312"));
				v_dept.add(dept_name);
			}
			this.closeConn();

			jcb=new JComboBox(v_dept);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	public void initialFrame()
	{
		this.setLayout(null);
		jlArray[0].setBounds(30,60,80,30);
		this.add(jlArray[0]);
		jcb.setBounds(120,60,200,30);
		this.add(jcb);
		jlArray[1].setBounds(30,110,80,30);
		this.add(jlArray[1]);
		jtf1.setBounds(120,110,150,30);
		this.add(jtf1);
		jlArray[2].setBounds(30,160,80,30);
		this.add(jlArray[2]);
		jtf2.setBounds(120,160,150,30);
		this.add(jtf2);
		jb1.setBounds(50,210,80,30);
		this.add(jb1);
		jb2.setBounds(160,210,80,30);
		this.add(jb2);
	}
	
	public void addListener()
	{
	    jcb.addActionListener(this);
	    jtf1.addActionListener(this);
	    jtf2.addActionListener(this);
		jb1.addActionListener(this);
		jb2.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jcb)
		{
			jtf1.requestFocus(true);
		}
		else if(e.getSource()==jtf1)
		{
			jtf2.requestFocus(true);
		}
		else if(e.getSource()==jtf2)
		{
			jb1.requestFocus(true);
		}
		else if(e.getSource()==jb1)
		{//按下"提交"按钮的处理代码

			String dept_name=(String)jcb.getSelectedItem();
			String class_id=jtf1.getText().trim();
			String patternStr="[0-9]{6}";
			
			if(class_id.equals(""))
			{
				JOptionPane.showMessageDialog(this,"请输入班号","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!class_id.matches(patternStr))
			{
				JOptionPane.showMessageDialog(this,"班号必须是六位数字","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}

			String class_name=jtf2.getText().trim();
			if(class_name.equals(""))
			{
				JOptionPane.showMessageDialog(this,"请输入班名","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(class_name.length()>6)
			{
				JOptionPane.showMessageDialog(this,"班名不能超过六个汉字","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try
			{
				//查询数据库判断该编号是否已经存在
				this.initialConnection();
				String sql="select * from class where class_id='"+class_id+"'";
				rs=stmt.executeQuery(sql);
				if(rs.next())
				{
					JOptionPane.showMessageDialog(this,"该编号的班已经存在","错误",JOptionPane.ERROR_MESSAGE);
					this.closeConn();
					return;
				}
				else
				{
					String sql1="insert into class values('"+class_id+"',(select dept_id from dept where "+
					 "dept_name='"+new String(dept_name.getBytes(),"gb2312")+"' and coll_id='"+coll_id+"'),'"+
					  coll_id+"','"+new String(class_name.getBytes(),"gb2312")+"')";
					 int i=stmt.executeUpdate(sql1); 
					 
					 if(i==1)
					 {
					 	JOptionPane.showMessageDialog(this,"添加成功","提示",JOptionPane.INFORMATION_MESSAGE);
					 }
					 else
					 {
					 	JOptionPane.showMessageDialog(this,"添加失败！！！","错误",JOptionPane.ERROR_MESSAGE);
					 }
				}

				this.closeConn();
			}
			catch(SQLException ea)
			{
				ea.printStackTrace();
			}
			catch(UnsupportedEncodingException eb)
			{
				eb.printStackTrace();
			}
		}
		
		else if(e.getSource()==jb2)
		{//按下重置按钮
			this.jtf1.setText("");
			this.jtf2.setText("");
		}
	}
	
	public void setFocus()
	{
		this.jcb.requestFocus(true);
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
}