import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
public class S_ChangePwd extends JPanel implements ActionListener
{

	private String host; 
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String stu_id;

	private JLabel[] jlArray={new JLabel("原始密码"),new JLabel("新密码"),new JLabel("确认新密码"),
	                         };
	private JPasswordField[] jpfArray={new JPasswordField(),new JPasswordField(),new JPasswordField()
	                             };
	private JButton[] jbArray={new JButton("确认"),new JButton("重置")
	                          };

	public S_ChangePwd(String stu_id,String host)
	{
		this.host=host;
		this.stu_id=stu_id;
		this.initialFrame();
		this.addListener();
	}

	public void addListener()
	{
		jpfArray[0].addActionListener(this);
		jpfArray[1].addActionListener(this);
		jpfArray[2].addActionListener(this);
		jbArray[0].addActionListener(this);
		jbArray[1].addActionListener(this);
	}

	public void initialFrame()
	{
		this.setLayout(null);
		for(int i=0;i<jlArray.length;i++)
		{
			jlArray[i].setBounds(30,20+50*i,150,30);
			this.add(jlArray[i]);
			jpfArray[i].setBounds(130,20+50*i,150,30);
			this.add(jpfArray[i]);
		}
		jbArray[0].setBounds(40,180,100,30);
		this.add(jbArray[0]);
		jbArray[1].setBounds(170,180,100,30);
		this.add(jbArray[1]);
	}

	public void actionPerformed(ActionEvent e)
	{
		
		if(e.getSource()==jpfArray[0])
		{
			jpfArray[1].requestFocus(true);
		}
		
		else if(e.getSource()==jpfArray[1])
		{
			jpfArray[2].requestFocus(true);
		}
		
		else if(e.getSource()==jpfArray[2])
		{
			jbArray[0].requestFocus(true);
		}
		
		else if(e.getSource()==jbArray[1])
		{
			for(int i=0;i<jpfArray.length;i++)
			{
				jpfArray[i].setText("");
			}
		}
		
		else if(e.getSource()==jbArray[0])
		{
			
			String patternStr="[0-9a-zA-Z]{6,12}";
			String oldPwd=jpfArray[0].getText();
			if(oldPwd.equals(""))
			{
				JOptionPane.showMessageDialog(this,"请输入原始密码","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}

			String newPwd=jpfArray[1].getText();
			
			if(newPwd.equals(""))
			{
				JOptionPane.showMessageDialog(this,"请输入新密码","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(!newPwd.matches(patternStr))
			{
				JOptionPane.showMessageDialog(this,"密码只能是6到12位的字母或数字","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}

			String newPwd1=jpfArray[2].getText();
			
			if(!newPwd.equals(newPwd1))
			{
				JOptionPane.showMessageDialog(this,"确认密码与新密码不符","错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try
			{ 
				this.initialConnection();
				String sql="update user_stu set pwd='"+newPwd+"' where stu_id='"+stu_id+"'"+
				           " and pwd='"+oldPwd+"'";
				int i=stmt.executeUpdate(sql);
				if(i==0)
				{
					JOptionPane.showMessageDialog(this,"修改失败，请检查您的密码是否正确","错误",JOptionPane.ERROR_MESSAGE);
				}
				else if(i==1)
				{
					JOptionPane.showMessageDialog(this,"密码修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
				}

				this.closeConn();
			}
			
			catch(Exception ea)
			{
				ea.printStackTrace();
			}
		}
	}
	
	public void setFocus()
	{
		jpfArray[0].requestFocus(true);
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