package entity;
import java.util.List;


public class Rfid_list {

	private String courseId;
	
	private List<RFID> rfid_list;
	
	public List<RFID> getRfid_list() {
		return rfid_list;
	}
	public void setRfid_list(List<RFID> rfid_list) {
		this.rfid_list = rfid_list;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	@Override
	public String toString() {
		return "Student_list [ rfid_list=" + rfid_list
				+ "]";
	}
	
}
