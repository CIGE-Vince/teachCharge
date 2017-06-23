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
	//host=���ݿ�����IP+":"+�˿ں�
	private String host;
	//����ѧԺ��ŵ�����
	String coll_id;
	
	//�������ĸ����ڵ�
	private DefaultMutableTreeNode dmtnRoot=new DefaultMutableTreeNode(new MyNode("����ѡ��","1"));
	private DefaultMutableTreeNode dmtn11=
	        new DefaultMutableTreeNode(new MyNode("��������","11"));
	private DefaultMutableTreeNode dmtn13=
	        new DefaultMutableTreeNode(new MyNode("������Ϣ��ѯ","13"));
	private DefaultMutableTreeNode dmtn14=
	        new DefaultMutableTreeNode(new MyNode("�ɼ���ѯ","14"));
	private DefaultMutableTreeNode dmtn15=
	        new DefaultMutableTreeNode(new MyNode("����ѡ������","15"));
	private DefaultMutableTreeNode dmtn16=
	        new DefaultMutableTreeNode(new MyNode("�γ̳ɼ�¼��","16"));
	private DefaultMutableTreeNode dmtn17=
	        new DefaultMutableTreeNode(new MyNode("��ӿγ�","17"));
	private DefaultMutableTreeNode dmtn18=
	        new DefaultMutableTreeNode(new MyNode("���Ӱ༶","18"));
	private DefaultMutableTreeNode dmtn20=
	        new DefaultMutableTreeNode(new MyNode("�����޸�","19"));
	private DefaultMutableTreeNode dmtn19=
	        new DefaultMutableTreeNode(new MyNode("�˳�","20"));
	
	//����һ������ģ��
	private DefaultTreeModel dtm=new DefaultTreeModel(dmtnRoot);
	private JTree jt=new JTree(dtm);
	
	//jspz����jpy����
	private JScrollPane jspz=new JScrollPane(jt);
	private JPanel jpy=new JPanel();
	private JSplitPane jsp1=new JSplitPane(
		                    JSplitPane.HORIZONTAL_SPLIT,jspz,jpy);
	
	//��������ģ��
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
	     welcome=new F_Welcome("�ɼ�����ϵͳ");
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
	
	//��Ƭ������ӹ���ģ��
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
		this.setTitle("��ʦ�ͻ���");
		Dimension screenSize = Toolkit.
		             getDefaultToolkit().getScreenSize();
		int centerX=screenSize.width/2;
		int centerY=screenSize.height/2;
		int w=900;
		int h=650;
		this.setBounds(centerX-w/2,centerY-h/2-30,w,h);
		this.setVisible(true);
	}
	
	//��Ƭ����չʾ����ģ��
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
					  {//��ӭҳ��
					        cl.show(jpy,"welcome");
					  }
	           	      else if(id.equals("20"))
	           	      {//�˳�ϵͳ
	           	            int i=JOptionPane.showConfirmDialog(jpy,
	           	                 "��ȷ��Ҫ�˳���ϵͳ��","ѯ��",
	           	                  JOptionPane.YES_NO_OPTION,
	           	                   JOptionPane.QUESTION_MESSAGE);
	           	      		if(i==0)
	           	      		{
	           	      			System.exit(0);
	           	      		}
	           	      }
	           	      else if(id.equals("19"))
	           	      {//��������ҳ��
	           	      	 cl.show(jpy,"changepwdteacher");
	           	      	 changepwdteacher.setFocus();
	           	      }
	           	      else if(id.equals("11"))
	           	      {//���ѧ��ҳ��
	           	      	 cl.show(jpy,"newstu");
	           	      	 newstu.setFocus();
	           	      }
	           	      else if(id.equals("13"))
	           	      {//ѧ����Ϣ��ѯҳ��
	           	      	 cl.show(jpy,"teachSearchInfo");
	           	      	 teachSearchInfo.setFocus();
	           	      }
	           	      else if(id.equals("14"))
	           	      {//�ɼ���ѯҳ��
	           	      	 cl.show(jpy,"stuscore");
	           	      	 stuscore.setFocus();
	           	      }
	           	      else if(id.equals("15"))
	           	      {//ѡ�ι���ҳ��
	           	      	 cl.show(jpy,"coursemanage");
	           	      	 coursemanage.updateTable();
	           	      }
	           	      else if(id.equals("16"))
	           	      {//�ɼ�¼��ҳ��
	           	      	 cl.show(jpy,"gradeindb");
	           	      }
	           	      else if(id.equals("17"))
	           	      {//��ӿγ�ҳ��
	           	      	   cl.show(jpy,"newcourse");
	           	      }
	           	      else if(id.equals("18"))
	           	      {//��Ӱ༶ҳ��
	           	      	  cl.show(jpy,"newclass");
	           	      	  newclass.setFocus();
	           	      }
	              }
	           }
		                       );
		//��չ���ڵ�������������Ϊ1
		jt.setToggleClickCount(1);
	}
	
	//���ڵ㶨��
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