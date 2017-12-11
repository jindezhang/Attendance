package controll;

import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

import dbutils.Trace;
import entity.Rfid_list;
import entity.Stu_list;
import entity.Student;
import entity.StudentDao;
import http.MyHttp;

public class GetStudent {

	private MyHttp http;
	
	private Gson gson;

	private StudentDao studentDao;
	private Stu_list bean;
	private List<Student> stu_list;


//	String device = ""
	public GetStudent(MyHttp http){
		this.http = http;
	}
	
	public void saveStudentAll() throws IOException{
		http.addParameter("data", "studentAll#sx101");
		http.doConnect();
		String json = http.getResponse();
		System.out.println(json);
		gson = new Gson();
		bean = gson.fromJson(json, Stu_list.class);
		stu_list = bean.getStu_list();
		Trace.print(stu_list);
		studentDao = new StudentDao();
		studentDao.saveStudenALL(stu_list);
	}
	
	public static void main(String[] args) throws Exception {
		MyHttp http = new MyHttp();
		GetStudent getStudent;
		String _url = "http://172.16.14.220:8080/"+
		"MvcTest3/Attendance/sumbit";
		http.openConnection(_url );
		getStudent = new GetStudent(http);
		getStudent.saveStudentAll();
	}
}
