package controll;


import com.google.gson.Gson;

import entity.RFID;
import entity.Rfid_list;


public class KQJJson {
	
	private Gson gson;
	
	public KQJJson() {
		// TODO Auto-generated constructor stub
		gson = new Gson();
	}
	//解析数据
	public Rfid_list getdata(String json){
		Rfid_list bean = gson.fromJson(json, Rfid_list.class);
		return bean;
	}
	//封装数据
	public String parseJson(RFID rfid){
		
		String json = gson.toJson(rfid);
		return json;
		
	}
	
 	public static void main(String[] args) {
		// TODO Auto-generated method stub
 		
	}

}
