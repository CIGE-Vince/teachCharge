import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import java.io.*;
import javax.swing.tree.*;
public class S_StuClient extends JFrame
{
	private String host;
	private String stu_id;

	private DefaultMutableTreeNode dmtnRoot=new DefaultMutableTreeNode(new MyNode("操作选项","1"));
	private DefaultMutableTreeNode dmtn11=new DefaultMutableTreeNode(new MyNode("个人基本信息","11"));
	private DefaultMutableTreeNode dmtn12=new DefaultMutableTreeNode(new MyNode("选课","12"));
	private DefaultMutableTreeNode dmtn13=new DefaultMutableTreeNode(new MyNode("课表显示","13"));
	private DefaultMutableTreeNode dmtn14=new DefaultMutableTreeNode(new MyNode("已修课程成绩","14"));
	private DefaultMutableTreeNode dmtn15=new DefaultMutableTreeNode(new MyNode("不及格课程成绩","15"));
	private DefaultMutableTreeNode dmtn19=new DefaultMutableTreeNode(new MyNode("密码修改","19"));
	private DefaultMutableTreeNode dmtn20=new DefaultMutableTreeNode(new MyNode("退出","20"));
	
	private DefaultTreeModel dtm=new DefaultTreeModel(dmtnRoot);
	private JTree jtree=new JTree(dtm);
	private JScrollPane jspz=new JScrollPane(jtree);
	private JPanel jpy=new JPanel();
	private JSplitPane jsp1=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jspz,jpy);
	private CardLayout cl;
	
	private F_Welcome welcome;
	private S_ChoseCourse chosecourse;
	private S_CourseTable coursetable;
	private S_StuGrade stugrade;
	private S_StuFailGrade stufailgrade;
	private S_StuInfo stuinfo;
	private S_ChangePwd changepwd;

	public S_StuClient(String stu_id,String host)
	{
		this.host=host;
		this.stu_id=stu_id;
		this.initialTree();
		this.initialPane();
		this.initialJpy();
		this.addListener();
		this.initialFrame();
	}

	public void initialJpy()
	{
		jpy.setLayout(new CardLayout());
		cl=(CardLayout)jpy.getLayout();
		jpy.add(welcome,"welcome");
		welcome.setBackground(Color.red);
		jpy.add(welcome,"welcome");
		jpy.add(chosecourse,"chosecourse");
		jpy.add(coursetable,"coursetable");
		jpy.add(stugrade,"stugrade");
		jpy.add(stufailgrade,"stufailgrade");
		jpy.add(stuinfo,"stuinfo");
		jpy.add(changepwd,"changepwd");
	}

	public void initialPane()
	{
		welcome=new F_Welcome("学生成绩管理系统");
		chosecourse=new S_ChoseCourse(stu_id,host);
		coursetable=new S_CourseTable(stu_id,host);
		stugrade=new S_StuGrade(stu_id,host);
		stufailgrade=new S_StuFailGrade(stu_id,host);
		stuinfo=new S_StuInfo(stu_id,host);
		changepwd=new S_ChangePwd(stu_id,host);
	}
	
	public void initialTree()
	{
		dmtnRoot.add(dmtn11);
		dmtnRoot.add(dmtn12);
		dmtnRoot.add(dmtn13);
		dmtnRoot.add(dmtn14);
		dmtnRoot.add(dmtn15);
		dmtnRoot.add(dmtn19);
		dmtnRoot.add(dmtn20);
		jtree.setToggleClickCount(1);
	}

	public void addListener()
	{
		jtree.addMouseListener(
               new MouseAdapter()
               {
               	  public void mouseClicked(MouseEvent e)
               	  { 
               	      DefaultMutableTreeNode dmtntemp=(DefaultMutableTreeNode)jtree.getLastSelectedPathComponent();
					  MyNode mynode=(MyNode)dmtntemp.getUserObject();
					  String id=mynode.getId();

					  if(id.equals("1"))
					  {
					  	    cl.show(jpy,"welcome");
					  }
               	      else if(id.equals("20"))
               	      {
               	      	    int i=JOptionPane.showConfirmDialog(jpy,"您确认要退出出系统吗？","询问",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
               	      		if(i==0)
               	      		{
               	      			System.exit(0);
               	      		}
               	      		
               	      }
               	      else if(id.equals("19"))
               	      {
               	      		cl.show(jpy,"changepwd");
               	      		changepwd.setFocus();
               	      }
               	      else if(id.equals("11"))
               	      {
               	      		cl.show(jpy,"stuinfo");
               	      }
               	      else if(id.equals("12"))
               	      {
               	      		cl.show(jpy,"chosecourse");
               	      }
               	      else if(id.equals("13"))
               	      {
               	      		cl.show(jpy,"coursetable");
               	      		coursetable.initialData();
               	      		coursetable.updataview();
               	      }
               	      else if(id.equals("14"))
               	      {
               	      		cl.show(jpy,"stugrade");
               	      }
               	      else if(id.equals("15"))
               	      {
               	      		cl.show(jpy,"stufailgrade");
               	      }
	              }
	           }
		                       );
	}	

	public void initialFrame()
	{
		this.add(jsp1);
		jsp1.setDividerLocation(200);
		jsp1.setDividerSize(4);
		this.setTitle("学生客户端");
		Image image=new ImageIcon("ico.gif").getImage();  
 		this.setIconImage(image);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=900;
		int h=650;
		this.setBounds(centerX-w/2,centerY-h/2-30,w,h);
		this.setVisible(true);
	}

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
}