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
	Vector v_dept=new Vector();//�������רҵ���Ƶ�Vector����

	private JLabel[] jlArray={new JLabel("��   ��   ��"),new JLabel("��   ��   ��"),
	new JLabel("ѧ         ��"),new JLabel("�� �� ר ҵ"),new JLabel("�Ƿ�����ѡ���б�")
	};

	private JTextField[] jtfArray={new JTextField(),new JTextField(),new JTextField()};
	private JComboBox jcb1;
	JButton jb1=new JButton("���");JButton jb2=new JButton("����");
	
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
		try{//����ѧԺ�Ż�ø�ѧԺ��רҵ��
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
		if(e.getSource()==jtfArray[0]){//������γ̺Żس���
			jtfArray[1].requestFocus(true);
		}
		else if(e.getSource()==jtfArray[1]){//������γ����س���
			jtfArray[2].requestFocus(true);
		}
		else if(e.getSource()==jtfArray[2]){//������ѧ�ֻس���
			jcb1.requestFocus(true);
		}
		else if(e.getSource()==jcb1){//�����б���ֵ�����仯ʱ
			jb1.requestFocus(true);
		}
		else if(e.getSource()==jb1)//����"���"�Ĵ������
		{
		    //�������Ŀγ̺�
			String cou_id=jtfArray[0].getText().trim();
			//�жϿγ̺��Ƿ���ȷ������ʽ�ַ���
			String patternStr="[0-9]{6}";
			
			if(cou_id.equals(""))
			{
				JOptionPane.showMessageDialog(this,"������ �γ̺�","����",
				                                JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!cou_id.matches(patternStr)){
				JOptionPane.showMessageDialog(this,"�γ̺ű�������λ����",
				                          "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//��ÿγ���
			String cou_name=jtfArray[1].getText().trim();
			if(cou_name.equals("")){
				JOptionPane.showMessageDialog(this,"������γ�����","����",
				                                JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(cou_name.length()>15){
				JOptionPane.showMessageDialog(this,"�γ����Ʋ��ܳ���ʮ����ַ�","����",
				                                   JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//���ѧ��
			String xuefen=jtfArray[2].getText().trim();
			if(xuefen.equals("")){
				JOptionPane.showMessageDialog(this,"������ѧ��","����",
				                               JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String patternStr1="[1-9]?[0-9](\\.[0-9])?";
			if(!xuefen.matches(patternStr1)){
				JOptionPane.showMessageDialog(this,"ѧ�ָ�ʽ����ȷ��С�������һλ��֮ǰ�����λ",
				                                   "����",JOptionPane.ERROR_MESSAGE);
			    return;
			}
			//���ѡ���רҵ����
			String dept_name=((String)jcb1.getSelectedItem()).trim();
			try{ 
				String sql="insert into course values('"+cou_id+"',"+
				"'"+new String(cou_name.getBytes(),"gb2312")+"',"+xuefen+",'"+coll_id+"',"+
				"(select dept_id from dept where dept_name="+
				"'"+new String(dept_name.getBytes(),"gb2312")+"' and coll_id='"+coll_id+"'))";
		
				this.initialConnection();
				int i=stmt.executeUpdate(sql);
				if(i!=1){
					JOptionPane.showMessageDialog(this,"���ʧ�ܣ�����","����",
					                               JOptionPane.ERROR_MESSAGE);
			    	return;
				}
				else{
					JOptionPane.showMessageDialog(this,"��ӳɹ�","��ʾ",
					                               JOptionPane.INFORMATION_MESSAGE);
				}
			}
			catch(Exception ea){
				JOptionPane.showMessageDialog(this,"���ʧ�ܣ�����,�ñ�ŵĿγ��Ѿ�����",
				                                "����",JOptionPane.ERROR_MESSAGE);
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