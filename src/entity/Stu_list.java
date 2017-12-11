package entity;

import java.util.List;

public class Stu_list {
	private List<Student> stu_list;

	public List<Student> getStu_list() {
		return stu_list;
	}

	public void setStu_list(List<Student> stu_list) {
		this.stu_list = stu_list;
	}

	@Override
	public String toString() {
		return "Stu_list [stu_list=" + stu_list + "]";
	} 

}
