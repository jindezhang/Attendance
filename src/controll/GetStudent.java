package controll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bean.MyStu_list;
import bean.MyStudent;

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
	private MyStu_list mybean;
	private List<Student> stu_list;


//	String device = ""
	public GetStudent(MyHttp http){
		this.http = http;
	}
	
	public void saveStudentAll() throws Exception{
		
		String json = http.deal_http(1, null);
		System.out.println("json"+json);
		gson = new Gson();
		bean = gson.fromJson(json, Stu_list.class);
		stu_list = bean.getStu_list();
		Trace.print(stu_list);
		studentDao = new StudentDao();
		studentDao.saveStudenALL(stu_list);
	}
	
	public void saveMyStudentAll() throws Exception{
		
		http.doMyConnect();
		String json = http.getResponse();
		System.out.println("json"+json);
		gson = new Gson();
		mybean = gson.fromJson(json, MyStu_list.class);
		stu_list = mystuTOstu(mybean.getStu_list());
		Trace.print(stu_list);
		studentDao = new StudentDao();
		studentDao.saveStudenALL(stu_list);
	}
	
	private List<Student> mystuTOstu(List<MyStudent> mystu){
		
		List<Student> list = new ArrayList<Student>();
		for(MyStudent ms : mystu){
			Student s = new Student();
			s.setIcon(ms.getIcon());
			s.setId(ms.getId());
			s.setName(ms.getName());
			s.setRfid(ms.getRfid());
			list.add(s);
		}
		
		return list;
		
	}
	public static void main(String[] args) throws Exception {
		MyHttp http = new MyHttp();
		GetStudent getStudent;
		String url1 = "http://172.16.15.185:8080/"+
		"MvcTest3/Attendance/sumbit";
		String url2 = "http://rj1033/api/v1/students";
		http.openConnection(url2 );
		getStudent = new GetStudent(http);
		getStudent.saveMyStudentAll();
	}
}
