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
import javax.swing.table.*;
public class T_CourseManage extends JPanel implements ActionListener
{   
	private String host;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private String coll_id;
	
	//创建存放课程表格与可选课程表格的表头与数据的Vector
	private Vector<String> columnNames1=new Vector<String>();
	private Vector<String> columnNames2=new Vector<String>();
	private Vector<Vector> rowData1=new Vector<Vector>();
	private Vector<Vector> rowData2=new Vector<Vector>();
	
	private JLabel[] jlArray={new JLabel("现有课程列表:"),new JLabel("已安排课程列表:"),
	                     new JLabel("请输入您要安排的课程的课程号:"),new JLabel("老师"),
	                     new JLabel("上课时间:"),new JLabel("星期"),new JLabel("讲次"),
	                     new JLabel("请输入您要移除的课程的课程号:"),new JLabel("星期"),
	                     new JLabel("讲次")
                        };
	
    private static final String[] xingqi={"1","2","3","4","5","6","7"};
    private static final String[] jiangci={"1","2","3","4","5"};
    
    private JButton[] jbArray={new JButton("提交该课程"),new JButton("移除该课程"),new JButton("允许选课"),new JButton("停止选课")};
    private JTextField[] jtfArray={new JTextField(),new JTextField(),new JTextField(),new JTextField()};
    private JComboBox[] jcbArray={new JComboBox(xingqi),new JComboBox(jiangci),
                                  new JComboBox(xingqi),new JComboBox(jiangci)};
  
    private JTable jt1=null;
    private JTable jt2=null;
    private JScrollPane jsp1=null;
    private JScrollPane jsp2=null;
	public T_CourseManage(String coll_id,String host)
	{
		this.host=host;
		this.coll_id=coll_id;
		
		//初始化数据
		this.initialData();
		this.initialFrame();
		this.initialListener();
	}

	public void initialListener()
	{
		jbArray[0].addActionListener(this);
		jbArray[1].addActionListener(this);
		jbArray[2].addActionListener(this);
		jbArray[3].addActionListener(this);
	}
	
	//初始化数据的方法 
	public void initialData()
	{
		this.initialHead();//初始化表头
		this.initialData1();//初始化课程表格的数据
		this.initialData2();//初始化可选课程表格的数据
	}
	
	public void initialFrame()
	{
		this.setLayout(null);
		//创建表格
		jt1=new JTable(new DefaultTableModel(rowData1,columnNames1));
		jt2=new JTable(new DefaultTableModel(rowData2,columnNames2));
		jsp1=new JScrollPane(jt1);
		jsp2=new JScrollPane(jt2);

		jlArray[0].setBounds(30,10,150,30);
		this.add(jlArray[0]);
		jsp1.setBounds(30,50,600,150);
		this.add(jsp1);
		jlArray[1].setBounds(30,210,150,30);
		this.add(jlArray[1]);
		jsp2.setBounds(30,250,600,150);
		this.add(jsp2);
		jlArray[2].setBounds(30,410,200,25);
		this.add(jlArray[2]);
		jtfArray[0].setBounds(230,410,100,25);
		this.add(jtfArray[0]);
		jlArray[3].setBounds(350,410,40,25);
		this.add(jlArray[3]);
		jtfArray[1].setBounds(390,410,150,25);
		this.add(jtfArray[1]);
		jlArray[4].setBounds(30,445,80,25);
		this.add(jlArray[4]);
		jlArray[5].setBounds(110,445,40,25);
		this.add(jlArray[5]);
		jcbArray[0].setBounds(150,445,40,25);
		this.add(jcbArray[0]);
		jlArray[6].setBounds(195,445,30,25);
		this.add(jlArray[6]);
		jcbArray[1].setBounds(225,445,40,25);
		this.add(jcbArray[1]);
		jbArray[0].setBounds(300,445,130,25);
		this.add(jbArray[0]);
		jlArray[7].setBounds(30,500,200,25);
		this.add(jlArray[7]);
		jtfArray[2].setBounds(230,500,100,25);
		this.add(jtfArray[2]);
		jlArray[8].setBounds(30,535,40,25);
		this.add(jlArray[8]);
		jcbArray[2].setBounds(70,535,40,25);
		this.add(jcbArray[2]);
		jlArray[9].setBounds(120,535,60,25);
		this.add(jlArray[9]);
		jcbArray[3].setBounds(147,535,40,25);
		this.add(jcbArray[3]);
		jbArray[1].setBounds(200,535,150,25);
		this.add(jbArray[1]);
		jbArray[2].setBounds(100,570,100,30);
		this.add(jbArray[2]);
		jbArray[3].setBounds(300,570,100,30);
		this.add(jbArray[3]);
	}
	
	public void initialData1()
	{//初始化课程表格数据的方法
		try
		{//查询数据库，获得开设的所有课程
			String sql="select cou_id,cou_name,xuefen,dept.dept_name from "+
			       "course,dept where dept.dept_id=course.dept_id and"+
			       " course.coll_id='"+coll_id+"'";
			this.initialConnection();
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				Vector v=new Vector();
				String cou_id=rs.getString(1);
				String cou_name=new String(rs.getString(2).getBytes("gb2312"));
				String xuefen=rs.getDouble(3)+"";
				String dept_name=new String(rs.getString(4).getBytes("gb2312"));
				v.add(cou_id);
				v.add(cou_name);
				v.add(xuefen);
				v.add(dept_name);
				this.rowData1.add(v);
			}
			rs.close();
		}
		catch(SQLException e)
		{e.printStackTrace();}
		catch(UnsupportedEncodingException e)
		{e.printStackTrace();}
	}
	
	public void initialData2()
	{//初始化可选课程表格的方法
		try
		{//获取可选课程
			String sql="select courseinfo.cou_id,course.cou_name,cou_day,"+
			            "cou_time,teacher from courseinfo,course where "+
			       "course.coll_id='"+coll_id+"' and courseinfo.cou_id=course.cou_id";
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				Vector v=new Vector();
				String cou_id=rs.getString(1);
				String cou_name=new String(rs.getString(2).getBytes("gb2312"));
				String cou_day=rs.getString(3);
				String cou_time=rs.getString(4);
				String teacher=new String(rs.getString(5).getBytes("gb2312"));
				v.add(cou_id);v.add(cou_name);v.add(cou_day);
				v.add(cou_time);v.add(teacher);
				this.rowData2.add(v);
			} 	           
		}
		catch(SQLException e)
		{e.printStackTrace();}
		catch(UnsupportedEncodingException e)
		{e.printStackTrace();}
		finally
		{this.closeConn();}
	}
	
	public void initialHead()
	{
		this.columnNames1.add("课程号");
		this.columnNames1.add("课程名");
		this.columnNames1.add("学分");
		this.columnNames1.add("所属专业");
		
		this.columnNames2.add("课程号");
		this.columnNames2.add("课程名");
		this.columnNames2.add("星期");
		this.columnNames2.add("讲次");
		this.columnNames2.add("任课老师");
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jbArray[0]){
		     this.submitCourse();
		}
		else if(e.getSource()==jbArray[1])
		{
			this.removeCourse();
		}
		else if(e.getSource()==jbArray[2]){
		   //将可选列表中的所有课程都设为学生可见的
		   this.permitChose();
		}
		else if(e.getSource()==jbArray[3]){
		    //将可选列表中的课程设为学生不可见
		    this.stopChose();
		}
	}
	
	public void submitCourse()
	{
		
		try{
			this.initialConnection();
			String cou_id=jtfArray[0].getText().trim();
			
			if(cou_id.equals("")){
				JOptionPane.showMessageDialog(this,"请输入课程号",
				                   "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Vector v=new Vector();
			String sql1="select dept_name,cou_name from dept,course "+
			           "where dept.dept_id=course.dept_id and "+
			           "course.cou_id='"+cou_id+"' and course.coll_id='"+coll_id+"'";
			 rs=stmt.executeQuery(sql1);
			 if(rs.next()){
			 	String dept_name=new String(rs.getString(1).getBytes("gbk"));
				String cou_name=new String(rs.getString(2).getBytes("gbk"));
				v.add(cou_id);
				v.add(cou_name);
			 }
			 
			 else{
			 	JOptionPane.showMessageDialog(this,"添加失败,本学院没有该课程",
			 	                            "错误",JOptionPane.ERROR_MESSAGE);
				return;
			 }
			 rs.close();
			 
			//获得开课时间与老师
			String cou_day=(String)jcbArray[0].getSelectedItem();
			String cou_time=(String)jcbArray[1].getSelectedItem();
			String teacher=jtfArray[1].getText().trim();
			if(teacher.equals("")){
				JOptionPane.showMessageDialog(this,"请输入教师","错误",
				                             JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//创建sql语句，并下添加信息写入数据库
			String sql="insert into courseinfo values"+
			            "('"+cou_id+"','"+cou_day+"','"+cou_time+"',"+
			            "'"+new String(teacher.getBytes(),"gbk")+"','0')";
			int i=stmt.executeUpdate(sql);
			if(i!=1){//添加失败
				JOptionPane.showMessageDialog(this,"添加失败，请检查是否与以后课程重复",
				                                    "错误",JOptionPane.ERROR_MESSAGE);
				return;
			}
			else{//添加成功，更新表格模型及视图
					v.add(cou_day);v.add(cou_time);v.add(teacher);
					DefaultTableModel temp=(DefaultTableModel)jt2.getModel();
					temp.getDataVector().add(v);
					((DefaultTableModel)jt2.getModel()).fireTableStructureChanged();
			}
		}
		catch(SQLException ea)
		{ea.printStackTrace();}
		catch(UnsupportedEncodingException ea)
		{ea.printStackTrace();}
		finally
		{this.closeConn();}
	}
	
	public void removeCourse()
	{
		String cou_id=jtfArray[2].getText().trim();
		if(cou_id.equals("")){
			JOptionPane.showMessageDialog(this,"请输入课程号","错误",
			                             JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		//获得要移除课程的上课时间
		String cou_day=(String)jcbArray[2].getSelectedItem();
		String cou_time=(String)jcbArray[3].getSelectedItem();
		
		try{//查看是否该课在可选列表并将其移除
			this.initialConnection();
			String sql1="select cou_name,teacher from courseinfo,course where "+
			      "course.cou_id=courseinfo.cou_id and course.cou_id='"+cou_id+"' and "+
			       "cou_day='"+cou_day+"' and cou_time='"+cou_time+"'";
			rs=stmt.executeQuery(sql1);
			if(rs.next()){
				String cou_name=new String(rs.getString(1).getBytes("gbk"));
				String teacher=new String(rs.getString(2).getBytes("gbk"));
				String sql="delete from courseinfo where cou_id='"+cou_id+"' and"+
				           " cou_day='"+cou_day+"' and cou_time='"+cou_time+"'";
				stmt.executeUpdate(sql);
				Vector v=new Vector();
				v.add(cou_id);v.add(cou_name);v.add(cou_day);
				v.add(cou_time);v.add(teacher);
				
				//更新表格视图显示
				DefaultTableModel temp=(DefaultTableModel)jt2.getModel();
				temp.getDataVector().remove(v);
				((DefaultTableModel)jt2.getModel()).fireTableStructureChanged();
			}
		}
		catch(SQLException ea)
		{ea.printStackTrace();}
		catch(UnsupportedEncodingException ea)
		{ea.printStackTrace();}
		finally
		{this.closeConn();}
	}
	
	public void permitChose()
	{
		String sql="update courseinfo,course set onchosing='1' where "+
		            "courseinfo.cou_id=course.cou_id "+
		            "and course.coll_id='"+coll_id+"'";
		try{
			this.initialConnection();
			stmt.executeUpdate(sql);
			this.closeConn();
			JOptionPane.showMessageDialog(this,"操作成功",
                    "操作提示",JOptionPane.INFORMATION_MESSAGE);
		}
		
		catch(Exception ea)
		{ea.printStackTrace();
		JOptionPane.showMessageDialog(this,"操作失败",
                "操作提示",JOptionPane.ERROR_MESSAGE);}
	}
	
	public void stopChose()
	{
		String sql="update courseinfo,course set onchosing='0' where"+
		             " courseinfo.cou_id=course.cou_id "+
		             "and course.coll_id='"+coll_id+"'";
		try{
			this.initialConnection();
			stmt.executeUpdate(sql);
			this.closeConn();
			JOptionPane.showMessageDialog(this,"操作成功",
                    "操作提示",JOptionPane.INFORMATION_MESSAGE);
		}
		catch(Exception ea)
		{ea.printStackTrace();
		JOptionPane.showMessageDialog(this,"操作失败",
                "操作提示",JOptionPane.ERROR_MESSAGE);}
	}
	
	//更新表格数据
	public void updateTable()
	{
		this.initialConnection();
		rowData1.removeAllElements();
		this.initialData1();
		((DefaultTableModel)jt1.getModel()).setDataVector(rowData1,columnNames1);
		((DefaultTableModel)jt1.getModel()).fireTableStructureChanged();
		this.closeConn();
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
		T_CourseManage cm=new T_CourseManage("01","127.0.0.1:3306");
		JFrame jf=new JFrame();
		jf.setBounds(10,10,700,650);
		jf.add(cm);
		jf.setVisible(true);
	}
}