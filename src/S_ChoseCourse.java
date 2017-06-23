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
public class S_ChoseCourse extends JPanel implements ActionListener
{   
	private String host;
	private String stu_id;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	
	private JLabel[] jlArray={new JLabel("��ѡ��Ŀγ��б����£�"),
	 new JLabel("��������Ҫѡ��γ̵Ŀγ̺�")};
	private JTextField jtf=new JTextField();
	private JButton jb=new JButton("��   ��");

	private JTable jt;private JScrollPane jsp;
	//������ű��ͷ�ͱ�����ݵ�Vector����
	private Vector<String> v_head=new Vector<String>();
	private Vector<Vector> v_data=new Vector<Vector>();
	//������ſ�ѡ�γ̵Ŀγ̺ŵ�Vector����
	private Vector<String> v_couid=new Vector<String>();
	
	public S_ChoseCourse(String stu_id,String host)
	{
		this.host=host;
		this.stu_id=stu_id;
		this.initialData();
		this.initialFrame();
	}
	
	public void initialFrame()
	{
		this.setLayout(null);
		DefaultTableModel dtm=new DefaultTableModel(v_data,v_head);
		jt=new JTable(dtm);
		jsp=new JScrollPane(jt);
		jlArray[0].setBounds(30,20,200,30);this.add(jlArray[0]);
		jsp.setBounds(20,70,650,400);this.add(jsp);
		jlArray[1].setBounds(30,500,190,30);this.add(jlArray[1]);
		jtf.setBounds(230,500,140,30);this.add(jtf);
		jtf.addActionListener(this);jb.setBounds(390,500,100,30);this.add(jb);
		jb.addActionListener(this);	
	}
	
	public void initialData()
	{   
		v_head.add("�γ̺�");v_head.add("�γ���");v_head.add("���ڼ�");v_head.add("�ڼ���");
		v_head.add("ѧ��");v_head.add("��ʦ");v_head.add("����רҵ");v_head.add("����ѧԺ");
		try{
			this.initialConnection();
			String sql="select courseinfo.cou_id,course.cou_name,courseinfo.cou_day,"+
			           "courseinfo.cou_time,course.xuefen,courseinfo.teacher,dept.dept_name,"+
			           "college.coll_name from course,courseinfo,dept,college where "+
			           "courseinfo.cou_id=course.cou_id and course.dept_id=dept.dept_id and "+
			           "course.coll_id=college.coll_id and courseinfo.onchosing='1'";
			rs=stmt.executeQuery(sql);
			
			while(rs.next()){
				Vector v=new Vector();
				String cou_id=rs.getString(1);
				v_couid.add(cou_id);
				String cou_name=new String(rs.getString(2).getBytes("gb2312"));
				String cou_day=rs.getString(3);
				String cou_time=rs.getString(4);
				String xuefen=rs.getDouble(5)+"";
				String teacher=new String(rs.getString(6).getBytes("gb2312"));
				String dept_name=new String(rs.getString(7).getBytes("gb2312"));
				String coll_name=new String(rs.getString(8).getBytes("gb2312"));
				v.add(cou_id);v.add(cou_name);v.add(cou_day);v.add(cou_time);
				v.add(xuefen);v.add(teacher);v.add(dept_name);v.add(coll_name);
				v_data.add(v);
			}
			rs.close();
		}
		catch(Exception e){e.printStackTrace();}
	}
	
	public void actionPerformed(ActionEvent e){
		
		if(e.getSource()==jb||e.getSource()==jtf)
		{//�����ύ��ť�Ĵ������
			String cou_id=jtf.getText().trim();
			if(cou_id.equals("")){//�γ̺�Ϊ��
				JOptionPane.showMessageDialog(this,"������γ̺�","����",
				                               JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(!v_couid.contains(cou_id)){
				JOptionPane.showMessageDialog(this,"��������ȷ�Ŀγ̺�","����",
				                                     JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try{
				String sql1="select * from grade where stu_id='"+stu_id+"' and "+
				             "cou_id='"+cou_id+"'";
				rs=stmt.executeQuery(sql1);
				
				if(rs.next()){
					JOptionPane.showMessageDialog(this,"���Ѿ�ѡ�����ſγ���","����",
					                                       JOptionPane.ERROR_MESSAGE);	
				}
				
				else{   //�ж��Ƿ����Ѿ�ѡ�Ŀγ�ʱ���ͻ
				    rs.close();
					String sql2="select cou_name from course,courseinfo,grade "+
					             "where grade.cou_id=course.cou_id "+
					            "and grade.cou_id=courseinfo.cou_id "+
					            "and grade.stu_id='"+stu_id+"' "+
					            "and grade.isdual=0 "+
					            "and (courseinfo.cou_day,courseinfo.cou_time) in "+
					            "(select cou_day,cou_time from courseinfo where "+
					            "cou_id='"+cou_id+"')";
					 rs=stmt.executeQuery(sql2);
					 
					 if(rs.next())
					 {
					 	String cou_name=new String(rs.getString(1).getBytes("gb2312"));
					 	JOptionPane.showMessageDialog(this,"��"+cou_name+"ʱ���ͻ","����",
					 	                                        JOptionPane.ERROR_MESSAGE);
					 }
					 
					 else
					 {//��ʼ��ӿγ�
					 	String sql="insert into grade(stu_id,cou_id) values"+
					 	            "('"+stu_id+"','"+cou_id+"')";
						int i=stmt.executeUpdate(sql);
						
						if(i==1)
						{
							JOptionPane.showMessageDialog(this,"��ӳɹ�","��ʾ",
							                            JOptionPane.INFORMATION_MESSAGE);
						}
						else
						{
							JOptionPane.showMessageDialog(this,"�ύʧ��","����",
							                                 JOptionPane.ERROR_MESSAGE);
						}
					 }
				}
				rs.close();
			}
			catch(Exception ea){ea.printStackTrace();}
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
		S_ChoseCourse cc=new S_ChoseCourse("514039927200","127.0.0.1:3306");
		JFrame jf=new JFrame();
		jf.setBounds(10,10,900,600);
		jf.add(cc);
		jf.setVisible(true);
	}
}