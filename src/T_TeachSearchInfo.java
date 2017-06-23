import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import java.io.*;
import javax.swing.tree.*;
import java.sql.*;
import java.util.Date;

public class T_TeachSearchInfo extends JPanel implements ActionListener
{
	private String host;

	private JLabel[] jlArray={new JLabel("学    号"),new JLabel("姓    名"),
	                          new JLabel("性    别"),new JLabel("出生日期"),
	                          new JLabel("籍    贯"),new JLabel("学    院"),
	                          new JLabel("专    业"),new JLabel("班    级"),
	                          new JLabel("入学时间"), new JLabel("年"),
	                          new JLabel("月"),new JLabel("日"),new JLabel("年"),
	                          new JLabel("月"),new JLabel("日")
	};

	private JLabel jl=new JLabel("请输入要查询学生的学号");
	private JTextField jtf=new JTextField();
	private JButton jb=new JButton("查询");
	
	//创建用于显示信息的标签数组
	private JLabel[] jlArray2=new JLabel[13];
	
	//声明GetStuInfo引用
	private F_GetStuInfo getsi;

	public T_TeachSearchInfo(String host)
	{
		this.host=host;
		getsi=new F_GetStuInfo(host);
		this.initialFrame();
	}

	public void initialFrame()
	{
		this.setLayout(null);
		jl.setBounds(30,20,150,30);
		this.add(jl);
		jtf.setBounds(175,20,150,30);
		this.add(jtf);
		
		jtf.addActionListener(this);
		jb.setBounds(330,20,100,30);
		this.add(jb);
		jb.addActionListener(this);
	}
	
	//实现ActionListener接口中的actionPerformed()方法
	public void actionPerformed(ActionEvent e)
	{
	
		if(e.getSource()==jb||e.getSource()==jtf)
		{
			//按下查询按钮的处理代码
			String stu_id=jtf.getText();
			if(stu_id.equals(""))
			{
				JOptionPane.showMessageDialog(this,"请输入学生学号","错误",
				                              JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			else
			{//调用GetStuInfo的方法获得指定学生的基本信息
				String[] baseinfo=getsi.getBaseInfo(stu_id);
				if(baseinfo[0]==null)
				{
					JOptionPane.showMessageDialog(this,"没有该学生","错误",
					                          JOptionPane.ERROR_MESSAGE);
					return;
				}
				else
				{//更新界面显示的所有控件
					this.removeAll();
					for(int i=0;i<13;i++)
					{
						jlArray2[i]=new JLabel(baseinfo[i]);
					}
					jl.setBounds(30,20,150,30);
					this.add(jl);
					jtf.setBounds(175,20,150,30);
					this.add(jtf);
					jb.setBounds(330,20,100,30);
					this.add(jb);
					jlArray[0].setBounds(30,100,100,30);
					this.add(jlArray[0]);
					jlArray[1].setBounds(30,150,100,30);
					this.add(jlArray[1]);
					jlArray[2].setBounds(30,200,100,30);
					this.add(jlArray[2]);
					jlArray[3].setBounds(30,250,100,30);
					this.add(jlArray[3]);
					jlArray[4].setBounds(30,300,100,30);
					this.add(jlArray[4]);
					jlArray[5].setBounds(30,350,100,30);
					this.add(jlArray[5]);
					jlArray[6].setBounds(30,400,100,30);
					this.add(jlArray[6]);
					jlArray[7].setBounds(30,450,100,30);
					this.add(jlArray[7]);
					jlArray[8].setBounds(30,500,100,30);
					this.add(jlArray[8]);
					jlArray2[0].setBounds(130,100,150,30);
					this.add(jlArray2[0]);
					jlArray2[1].setBounds(130,150,150,30);
					this.add(jlArray2[1]);
					jlArray2[6].setBounds(130,300,500,30);
					this.add(jlArray2[6]);
					jlArray2[2].setBounds(130,200,60,30);
					this.add(jlArray2[2]);
					jlArray2[3].setBounds(130,250,55,30);
					this.add(jlArray2[3]);
					jlArray[9].setBounds(185,250,20,30);
					this.add(jlArray[9]);
					jlArray2[4].setBounds(205,250,45,30);
					this.add(jlArray2[4]);
					jlArray[10].setBounds(250,250,20,30);
					this.add(jlArray[10]);
					jlArray2[5].setBounds(270,250,45,30);
					this.add(jlArray2[5]);
					jlArray[11].setBounds(315,250,20,30);
					this.add(jlArray[11]);
					jlArray2[7].setBounds(130,350,200,30);
					this.add(jlArray2[7]);
					jlArray2[8].setBounds(130,400,200,30);
					this.add(jlArray2[8]);
					jlArray2[9].setBounds(130,450,100,30);
					this.add(jlArray2[9]);
					jlArray2[10].setBounds(130,500,55,30);
					this.add(jlArray2[10]);
					jlArray[12].setBounds(185,500,20,30);
					this.add(jlArray[12]);
					jlArray2[11].setBounds(205,500,45,30);
					this.add(jlArray2[11]);
					jlArray[13].setBounds(250,500,20,30);
					this.add(jlArray[13]);
					jlArray2[12].setBounds(270,500,45,30);
					this.add(jlArray2[12]);
					jlArray[14].setBounds(315,500,20,30);
					this.add(jlArray[14]);
					this.repaint();
				}
			}
		}
	}
	
	public void setFocus()
	{
		this.jtf.requestFocus(true);
	}
	
	public static void main(String args[])
	{
		T_TeachSearchInfo tsi=new T_TeachSearchInfo("127.0.0.1:3306");
		JFrame jf=new JFrame();
		jf.setBounds(10,10,700,650);
		jf.add(tsi);
		jf.setVisible(true);
	}
}