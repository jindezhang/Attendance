package entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;

import org.junit.Test;

import dbutils.*;

public class StudentDao extends BaseDao<Student> {
	
	public Student findMonkeys(Student stu){
		System.out.println("Student--->findStudent");
		return null;
	}

	public void addStudent( Student stu ) 
			throws SQLException{
		SqlData data = new SqlData(
				"rfid,id,name,icon", 
				"?,?,?,?" );
		Connection conn = getConnection();
		addNew( conn, data, stu );
		closeConnection(conn);
	}
	public int deleteStudent(String rfid) throws SQLException{
		String sql = "delete from Student where rfid =?";
		Connection conn = getConnection();
		int count = save(conn, sql, rfid);
		closeConnection( conn );
		return count;
		
	}
	public List<Student> getStudent() throws SQLException{
		String sql = "select * from Student order by id";
		Connection conn = getConnection();
		List<Student> list = getList(conn, sql);
		closeConnection( conn );
		return list;
	}
	public Student getStudentById(String rfid) throws SQLException{
		String sql = "select * from Student where rfid=?";
		Connection conn = getConnection();
		Student student = get( conn, sql, rfid );
		closeConnection( conn );
		return student;
	}
	
	public void saveStudenALL(List<Student> stu){
		for(Student s : stu){
			try {
				addStudent(s);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
/*
	@Test
	public void testList(){
		StudentDao ud = new StudentDao();
		List<Student> student = null;
		try {
			student = ud.getStudent();
			for(Student m : student){
				Trace.print( m );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Trace.print("[Test End]");
	}
	
	@Test
	public void testget(){
		StudentDao ud = new StudentDao();
		Student student = null;
		try {
			student = ud.getStudentById("7AFCFD5E1");
			if(student == null){
				
			}else{
				Trace.print(student);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Trace.print("[TestGET End]");
	}
	*/
	/*
	 * Check[ok]
	 */
	@Test
	public void testAdd() throws SQLException{
		StudentDao sd = new StudentDao();
		Student stu = new Student();
		stu.setId("201");
		
		stu.setName("张三");
		stu.setRfid("7AFCFD5E");
		stu.setIcon("icon\\1.jpg");
		Trace.print(stu);
		sd.addStudent(stu);
		
		
		stu.setId("2011");
		stu.setName("张说");
		stu.setRfid("7AFCFD5");
		stu.setIcon("icon\\2.jpg");
		sd.addStudent(stu);
		
		stu.setId("2012");
		stu.setName("张2");
		stu.setRfid("7AFCFD5D");
		stu.setIcon("icon\\3.jpg");
		sd.addStudent(stu);
		
		stu.setId("2013");
		stu.setName("张四");
		stu.setRfid("7AEA96CE");
		stu.setIcon("icon\\4.jpg");
		sd.addStudent(stu);
		
	}
	
}

	

/*
	*[Checked OK]
	Monkeys user = new Monkeys();
	user.setAge(0);
	user.setGender("gen");
	user.setId(50);
	user.setName("sun");
	try {
		ud.addMonkeys(user);
	} catch (SQLException e) {
		e.printStackTrace();
	}
*/
