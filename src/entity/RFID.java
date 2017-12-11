package entity;

public class RFID {
		private String rfid;

		public String getRfid() {
			return rfid;
		}

		public void setRfid(String rfid) {
			this.rfid = rfid;
		}

		@Override
		public String toString() {
			return "RFID [rfid=" + rfid + "]";
		}
	
	}