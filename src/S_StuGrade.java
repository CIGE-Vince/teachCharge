import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.*;
public class S_StuGrade extends JPanel
{
	private String host;
	private String stu_id;
	private JLabel jl1=new JLabel("已修学分：");
	private JLabel jl2=new JLabel("");
	private JTable jt;
	private JScrollPane jsp;

	private Vector v_head=new Vector();
	private Vector v_data=new Vector();

	private F_GetScore gs;

	public S_StuGrade(String stu_id,String host)
	{
		this.host=host;
		this.stu_id=stu_id;
		gs=new F_GetScore(host);
		this.initialData();
		this.initialFrame();
	}

	public void initialData()
	{

		v_head.add("课程名");
		v_head.add("分数");
		v_head.add("学分");

		v_data=gs.getAllScore(stu_id);

		String xuefen=gs.getXueFen(stu_id)+"";
		jl2.setText(xuefen);
	}

	public void initialFrame()
	{   
		this.setLayout(null);
		DefaultTableModel dtm=new DefaultTableModel(v_data,v_head);
		jt=new JTable(dtm);
		jsp=new JScrollPane(jt);
		jsp.setBounds(60,30,500,500);
		this.add(jsp);
		jl1.setBounds(60,540,130,30);
		this.add(jl1);
		jl2.setBounds(200,540,130,30);
		this.add(jl2);
	}
}