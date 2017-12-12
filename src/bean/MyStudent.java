package bean;

public class MyStudent {

	private String id;
	private String name;
	private String rfid;
	private String squad;
	private String icon;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRfid() {
		return rfid;
	}
	public void setRfid(String rfid) {
		this.rfid = rfid;
	}
	public String getSquad() {
		return squad;
	}
	public void setSquad(String squad) {
		this.squad = squad;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Override
	public String toString() {
		return "MyStudent [id=" + id + ", name=" + name + ", rfid=" + rfid
				+ ", squad=" + squad + ", icon=" + icon + "]";
	}
	
}
