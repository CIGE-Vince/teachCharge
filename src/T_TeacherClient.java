import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import java.io.*;
import javax.swing.tree.*;

public class T_TeacherClient extends JFrame
{
	//host=数据库主机IP+":"+端口号
	private String host;
	//声明学院编号的引用
	String coll_id;
	
	//创建树的各个节点
	private DefaultMutableTreeNode dmtnRoot=new DefaultMutableTreeNode(new MyNode("操作选项","1"));
	private DefaultMutableTreeNode dmtn11=
	        new DefaultMutableTreeNode(new MyNode("新生报到","11"));
	private DefaultMutableTreeNode dmtn13=
	        new DefaultMutableTreeNode(new MyNode("基本信息查询","13"));
	private DefaultMutableTreeNode dmtn14=
	        new DefaultMutableTreeNode(new MyNode("成绩查询","14"));
	private DefaultMutableTreeNode dmtn15=
	        new DefaultMutableTreeNode(new MyNode("开课选项设置","15"));
	private DefaultMutableTreeNode dmtn16=
	        new DefaultMutableTreeNode(new MyNode("课程成绩录入","16"));
	private DefaultMutableTreeNode dmtn17=
	        new DefaultMutableTreeNode(new MyNode("添加课程","17"));
	private DefaultMutableTreeNode dmtn18=
	        new DefaultMutableTreeNode(new MyNode("增加班级","18"));
	private DefaultMutableTreeNode dmtn20=
	        new DefaultMutableTreeNode(new MyNode("密码修改","19"));
	private DefaultMutableTreeNode dmtn19=
	        new DefaultMutableTreeNode(new MyNode("退出","20"));
	
	//创建一棵树的模型
	private DefaultTreeModel dtm=new DefaultTreeModel(dmtnRoot);
	private JTree jt=new JTree(dtm);
	
	//jspz在左，jpy在右
	private JScrollPane jspz=new JScrollPane(jt);
	private JPanel jpy=new JPanel();
	private JSplitPane jsp1=new JSplitPane(
		                    JSplitPane.HORIZONTAL_SPLIT,jspz,jpy);
	
	//创建功能模块
	private F_Welcome welcome;
	private T_ChangePwdTeacher changepwdteacher;
	private T_NewStu newstu;
	private T_TeachSearchInfo teachSearchInfo;
	private T_StuScore stuscore;
	private T_CourseManage coursemanage;
	private T_GradeManage gradeindb;
	private T_NewCourse newcourse;
	private T_NewClass newclass;

	CardLayout cl;
	public T_TeacherClient(String coll_id,String host)
	{
		this.host=host;
		this.coll_id=coll_id;
		this.initialTree();
		this.initialPanel();
		this.addListener();
		this.initialJpy();
		this.initialFrame();
	}
	
	public void initialPanel()
	{
	     welcome=new F_Welcome("成绩管理系统");
	     changepwdteacher=new T_ChangePwdTeacher(host);
	     newstu=new T_NewStu(coll_id,host);
	     teachSearchInfo=new T_TeachSearchInfo(host);
	     stuscore=new T_StuScore(host);
	     coursemanage=new T_CourseManage(coll_id,host);
	     gradeindb=new T_GradeManage(coll_id,host);
	     newcourse=new T_NewCourse(coll_id,host);
	     newclass=new T_NewClass(coll_id,host);
	}

	public void initialTree()
	{
		dmtnRoot.add(dmtn11);
		dmtnRoot.add(dmtn13);
		dmtnRoot.add(dmtn14);
		dmtnRoot.add(dmtn15);
		dmtnRoot.add(dmtn16);
		dmtnRoot.add(dmtn17);
		dmtnRoot.add(dmtn18);		
		dmtnRoot.add(dmtn20);	
		dmtnRoot.add(dmtn19);
	}
	
	//卡片布局添加功能模块
	public void initialJpy()
	{
		jpy.setLayout(new CardLayout());
		cl=(CardLayout)jpy.getLayout();
		jpy.add(welcome,"welcome");
		jpy.add(changepwdteacher,"changepwdteacher");
		jpy.add(newstu,"newstu");
		jpy.add(teachSearchInfo,"teachSearchInfo");
		jpy.add(stuscore,"stuscore");
		jpy.add(coursemanage,"coursemanage");
		jpy.add(gradeindb,"gradeindb");
		jpy.add(newcourse,"newcourse");
		jpy.add(newclass,"newclass");
	}
	
	public void initialFrame()
	{   
		this.add(jsp1);
		jsp1.setDividerLocation(200);
		jsp1.setDividerSize(4);
		Image image=new ImageIcon("ico.gif").getImage();  
		this.setIconImage(image);
		this.setTitle("教师客户端");
		Dimension screenSize = Toolkit.
		             getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=900;
		int h=650;
		this.setBounds(centerX-w/2,centerY-h/2-30,w,h);
		this.setVisible(true);
	}
	
	//卡片布局展示功能模块
	public void addListener()
	{
		jt.addMouseListener(
	           new MouseAdapter()
	           {
	           	  public void mouseClicked(MouseEvent e)
	           	  { 
	           	      DefaultMutableTreeNode dmtntemp=
	           	      (DefaultMutableTreeNode)jt.
	           	      getLastSelectedPathComponent();
					  MyNode mynode=(MyNode)dmtntemp.getUserObject();
					  String id=mynode.getId();
	
					  if(id.equals("1"))
					  {//欢迎页面
					        cl.show(jpy,"welcome");
					  }
	           	      else if(id.equals("20"))
	           	      {//退出系统
	           	            int i=JOptionPane.showConfirmDialog(jpy,
	           	                 "您确认要退出出系统吗？","询问",
	           	                  JOptionPane.YES_NO_OPTION,
	           	                   JOptionPane.QUESTION_MESSAGE);
	           	      		if(i==0)
	           	      		{
	           	      			System.exit(0);
	           	      		}
	           	      }
	           	      else if(id.equals("19"))
	           	      {//更改密码页面
	           	      	 cl.show(jpy,"changepwdteacher");
	           	      	 changepwdteacher.setFocus();
	           	      }
	           	      else if(id.equals("11"))
	           	      {//添加学生页面
	           	      	 cl.show(jpy,"newstu");
	           	      	 newstu.setFocus();
	           	      }
	           	      else if(id.equals("13"))
	           	      {//学生信息查询页面
	           	      	 cl.show(jpy,"teachSearchInfo");
	           	      	 teachSearchInfo.setFocus();
	           	      }
	           	      else if(id.equals("14"))
	           	      {//成绩查询页面
	           	      	 cl.show(jpy,"stuscore");
	           	      	 stuscore.setFocus();
	           	      }
	           	      else if(id.equals("15"))
	           	      {//选课管理页面
	           	      	 cl.show(jpy,"coursemanage");
	           	      	 coursemanage.updateTable();
	           	      }
	           	      else if(id.equals("16"))
	           	      {//成绩录入页面
	           	      	 cl.show(jpy,"gradeindb");
	           	      }
	           	      else if(id.equals("17"))
	           	      {//添加课程页面
	           	      	   cl.show(jpy,"newcourse");
	           	      }
	           	      else if(id.equals("18"))
	           	      {//添加班级页面
	           	      	  cl.show(jpy,"newclass");
	           	      	  newclass.setFocus();
	           	      }
	              }
	           }
		                       );
		//将展开节点的鼠标点击次数设为1
		jt.setToggleClickCount(1);
	}
	
	//树节点定义
	class MyNode
	{
		private String values;
		private String id;
		public MyNode(String values,String id)
		{
			this.values=values;
			this.id=id;
		}
		public String toString()
		{
			return this.values;
		}
		public String getId()
		{
			return this.id;
		}
	}
	
	public static void main(String args[])	
	{
		new T_TeacherClient("01","127.0.0.1:3306");
	}
}