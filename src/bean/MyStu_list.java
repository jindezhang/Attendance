package bean;

import java.util.List;


public class MyStu_list {
	private List<MyStudent> stu_list;

	public List<MyStudent> getStu_list() {
		return stu_list;
	}

	public void setStu_list(List<MyStudent> stu_list) {
		this.stu_list = stu_list;
	}

	@Override
	public String toString() {
		return "MyStu_list [stu_list=" + stu_list + "]";
	}

	

}
