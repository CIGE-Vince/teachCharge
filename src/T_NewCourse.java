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
public class T_NewCourse extends JPanel implements ActionListener
{
	private String host;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String coll_id;
	Vector v_dept=new Vector();//创建存放专业名称的Vector对象

	private JLabel[] jlArray={new JLabel("课   程   号"),new JLabel("课   程   名"),
	new JLabel("学         分"),new JLabel("所 属 专 业"),new JLabel("是否列入选课列表")
	};

	private JTextField[] jtfArray={new JTextField(),new JTextField(),new JTextField()};
	private JComboBox jcb1;
	JButton jb1=new JButton("添加");JButton jb2=new JButton("重置");
	
	public T_NewCourse(String coll_id,String host)
	{
		this.host=host;
		this.coll_id=coll_id;
		this.initialData();
		this.addListener();
		this.initialFrame();
	}
	
	public void initialData()
	{
		try{//根据学院号获得该学院的专业名
			this.initialConnection();
			String sql="select dept_name from dept where coll_id='"+coll_id+"'";
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				String dept_name=new String(rs.getString(1).getBytes("gb2312"));
				v_dept.add(dept_name);
			}
			jcb1=new JComboBox(v_dept);
			this.closeConn();
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void addListener()
	{
	    jtfArray[0].addActionListener(this);
	    jtfArray[1].addActionListener(this);
	    jtfArray[2].addActionListener(this);
	    jcb1.addActionListener(this);
		jb1.addActionListener(this);
		jb2.addActionListener(this);
	}
	
	public void initialFrame()
	{
		this.setLayout(null);
		jlArray[0].setBounds(30,50,100,30);
		this.add(jlArray[0]);
		jlArray[1].setBounds(30,100,100,30);
		this.add(jlArray[1]);
		jlArray[2].setBounds(30,150,100,30);
		this.add(jlArray[2]);
		jlArray[3].setBounds(30,200,100,30);
		this.add(jlArray[3]);
		jtfArray[0].setBounds(180,50,150,30);
		this.add(jtfArray[0]);
		jtfArray[1].setBounds(180,100,150,30);
		this.add(jtfArray[1]);
		jtfArray[2].setBounds(180,150,150,30);
		this.add(jtfArray[2]);
		jcb1.setBounds(180,200,150,30);
		this.add(jcb1);
		jb1.setBounds(50,250,80,30);
		jb2.setBounds(200,250,80,30);
		this.add(jb1);
		this.add(jb2);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jtfArray[0]){//输入完课程号回车后
			jtfArray[1].requestFocus(true);
		}
		else if(e.getSource()==jtfArray[1]){//输入完课程名回车后
			jtfArray[2].requestFocus(true);
		}
		else if(e.getSource()==jtfArray[2]){//输入完学分回车后
			jcb1.requestFocus(true);
		}
		else if(e.getSource()==jcb1){//下拉列表框的值发生变化时
			jb1.requestFocus(true);
		}
		else if(e.getSource()==jb1)//按下"添加"的处理代码
		{
		    //获得输入的课程号
			String cou_id=jtfArray[0].getText().trim();
			//判断课程号是否正确的正则式字符串
			String patternStr="[0-9]{6}";
			
			if(cou_id.equals(""))
			{
				JOptionPane.showMessageDialog(this,"请输入 课程号","错误",
				                                JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!cou_id.matches(patternStr)){
				JOptionPane.showMessageDialog(this,"课程号必须是六位数字",
				                          "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//获得课程名
			String cou_name=jtfArray[1].getText().trim();
			if(cou_name.equals("")){
				JOptionPane.showMessageDialog(this,"请输入课程名称","错误",
				                                JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(cou_name.length()>15){
				JOptionPane.showMessageDialog(this,"课程名称不能超过十五个字符","错误",
				                                   JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//获得学分
			String xuefen=jtfArray[2].getText().trim();
			if(xuefen.equals("")){
				JOptionPane.showMessageDialog(this,"请输入学分","错误",
				                               JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String patternStr1="[1-9]?[0-9](\\.[0-9])?";
			if(!xuefen.matches(patternStr1)){
				JOptionPane.showMessageDialog(this,"学分格式不正确，小数后最多一位，之前最多两位",
				                                   "错误",JOptionPane.ERROR_MESSAGE);
			    return;
			}
			//获得选择的专业名称
			String dept_name=((String)jcb1.getSelectedItem()).trim();
			try{ 
				String sql="insert into course values('"+cou_id+"',"+
				"'"+new String(cou_name.getBytes(),"gb2312")+"',"+xuefen+",'"+coll_id+"',"+
				"(select dept_id from dept where dept_name="+
				"'"+new String(dept_name.getBytes(),"gb2312")+"' and coll_id='"+coll_id+"'))";
		
				this.initialConnection();
				int i=stmt.executeUpdate(sql);
				if(i!=1){
					JOptionPane.showMessageDialog(this,"添加失败！！！","错误",
					                               JOptionPane.ERROR_MESSAGE);
			    	return;
				}
				else{
					JOptionPane.showMessageDialog(this,"添加成功","提示",
					                               JOptionPane.INFORMATION_MESSAGE);
				}
			}
			catch(Exception ea){
				JOptionPane.showMessageDialog(this,"添加失败！！！,该编号的课程已经存在",
				                                "错误",JOptionPane.ERROR_MESSAGE);
			}
			finally{
				this.closeConn();
			}
		}
		
		else if(e.getSource()==jb2){
			for(int i=0;i<jtfArray.length;i++){
				jtfArray[i].setText("");
			}
		}
	}
	
	public void setFocus(){
		this.jtfArray[0].requestFocus(true);
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
	
	public static void main(String args[])
	{
		T_NewCourse nc=new T_NewCourse("01","127.0.0.1:3306");
		JFrame jf=new JFrame();
		jf.setBounds(10,10,700,650);
		jf.add(nc);
		jf.setVisible(true);
	}
}