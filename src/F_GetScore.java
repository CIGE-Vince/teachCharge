import java.util.*;
import java.sql.*;
public class F_GetScore
{
	private String host;
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	
	//创建存放返回结果的Vector
	private Vector<Vector> v=new Vector<Vector>();
	//创建存放学生学分数的变量
	private double xuefen;
	
	public F_GetScore(String host)
	{
		this.host=host;
	}
	
	//根据学号获得学生已修课程信息的方法
	public Vector getAllScore(String stu_id)
	{//将Vector清空，防止有冗余记录
		v.removeAllElements();
		try
		{//查询数据库，将数据存入Vector v并返回
			this.initialConnection();
			String sql=
			"select course.cou_name,grade.score,course.xuefen from course,grade"+
			" where grade.stu_id='"+stu_id+"' and grade.isdual=1 and "+
			 "grade.cou_id=course.cou_id order by score desc";
			rs=stmt.executeQuery(sql);
			
			while(rs.next())
			{
				
				Vector temp=new Vector();
				String cou_name=new String(rs.getString(1).getBytes("gb2312"));
				String score=rs.getDouble(2)+"";
				String xuefen=rs.getDouble(3)+"";
				temp.add(cou_name);
				temp.add(score);
				temp.add(xuefen);	
				v.add(temp);
			}           
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.closeConn();
		}
		return v;
	}

	public Vector getFailScore(String stu_id)
	{ //将Vector清空，防止有冗余记录  
		v.removeAllElements();
		try
		{//查询数据库，将不及格课程信息存入Vecteo v并返回
			this.initialConnection();
			String sql=
					"select course.cou_name,grade.score,"+
					"course.xuefen from course,grade "+
					"where grade.stu_id='"+stu_id+"' and"+
					" grade.cou_id=course.cou_id and score<60"+
					 " and grade.isdual=1";
			rs=stmt.executeQuery(sql);
			while(rs.next())
			{
				Vector temp=new Vector();
				String cou_name=new String(rs.getString(1).getBytes("gb2312"));
				String score=rs.getDouble(2)+"";
				String xuefen=rs.getDouble(3)+"";
				temp.add(cou_name);
				temp.add(score);
				temp.add(xuefen);
				v.add(temp);	
			}           
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.closeConn();
		}
		return v;
	}
	
	//根据学号获得所修学分
	public double getXueFen(String stu_id)
	{
		try
		{
			this.initialConnection();
			String sql=
			"select sum(xuefen) from course,grade where stu_id='"+stu_id+"'"+
			" and grade.cou_id=course.cou_id and grade.score>=60 and isdual=1";
			rs=stmt.executeQuery(sql);
			if(rs.next())
			{//获取第一列的值
				xuefen=rs.getDouble(1);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return xuefen;
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
}