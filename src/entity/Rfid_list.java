package entity;
import java.util.List;


public class Rfid_list {

	private List<RFID> rfid_list;
	
	public List<RFID> getRfid_list() {
		return rfid_list;
	}
	public void setRfid_list(List<RFID> rfid_list) {
		this.rfid_list = rfid_list;
	}
	@Override
	public String toString() {
		return "Student_list [ rfid_list=" + rfid_list
				+ "]";
	}
	
}
