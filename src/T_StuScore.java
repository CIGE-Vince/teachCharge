import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
public class T_StuScore extends JPanel implements ActionListener
{
	private String host;
	//创建提示标签
	private JLabel jl=new JLabel("请输入学生学号：");
	private JLabel jl1=new JLabel("已修学分：");
	//创建用于显示学分的标签
	private JLabel jl2=new JLabel("");
	//创建用于输入学号的文本框
	private JTextField jtf=new JTextField();
	//创建动作按钮
	private JButton jb=new JButton("查询");
	//声明表格引用
	private JTable jt;
	private JScrollPane jsp;
	//创建用于存放表格数据的Vector
	private Vector v_head=new Vector();
	private Vector v_data=new Vector();
	//创建GetScore对象
	private F_GetScore gs;

	public T_StuScore(String host)
	{
		this.host=host;
		gs=new F_GetScore(host);
		this.initialData();
		this.initialFrame();
	}

	public void initialData()
	{
		//初始化表头
		v_head.add("课程名");
		v_head.add("分数");
		v_head.add("学分");
	}

	public void initialFrame()
	{
		this.setLayout(null);
		jl.setBounds(60,20,150,30);
		this.add(jl);
		jtf.setBounds(195,20,150,30);
		this.add(jtf);
		jtf.addActionListener(this);
		jb.setBounds(350,20,100,30);
		this.add(jb);
		jb.addActionListener(this);
		
		DefaultTableModel dtm=new DefaultTableModel(v_data,v_head);
		jt=new JTable(dtm);
		jsp=new JScrollPane(jt);
		jsp.setBounds(60,60,500,500);
		this.add(jsp);
		jl1.setBounds(60,570,130,30);
		this.add(jl1);
		jl2.setBounds(200,570,130,30);
		this.add(jl2);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==jb||e.getSource()==jtf)
		{//按下查询按钮的处理代码

			String stu_id=jtf.getText().trim();
			if(stu_id.equals(""))
			{
				JOptionPane.showMessageDialog(this,"请输入学生学号","错误",
				                               JOptionPane.ERROR_MESSAGE);
				return;
			}
			else
			{
				//根据学号调用GetScore的方法获得该学生的成绩信息
				v_data=gs.getAllScore(stu_id);
				//更新表格模型
				DefaultTableModel dtm=new DefaultTableModel(v_data,v_head);
				jt.setModel(dtm);
				//更新显示
				((DefaultTableModel)jt.getModel()).fireTableStructureChanged();
				String xuefen=gs.getXueFen(stu_id)+"";
				//显示学分信息
				jl2.setText(xuefen);
			}
		}
	}
	
	public void setFocus()
	{
		this.jtf.requestFocus(true);
	}
	
	public static void main(String args[])
	{
		T_StuScore ss=new T_StuScore("127.0.0.1:3306");
		JFrame jf=new JFrame();
		jf.setBounds(10,10,700,650);
		jf.add(ss);
		jf.setVisible(true);
	}
}
