package Thread;

import http.MyHttp;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import UI.KQGui;
import controll.KQJJson;
import controll.KQtime;
import controll.ReadRFID;
import dbutils.Trace;
import entity.RFID;
import entity.Student;
import entity.StudentDao;

public class Check extends Thread {

	  Map<String,Integer> rfid_list;
	  KQGui kqjUI;
	  MyHttp http = null;
	  ReadRFID rRFID;//读取卡号对象。
	  String mode;
	  KQtime kqjTime;

	public Check( KQGui kqjUI,ReadRFID rRFID,KQtime kqjTime
			  ) 
	{
		this.kqjTime = kqjTime;		
		this.rRFID = rRFID;
		this.kqjUI = kqjUI;
		mode = "local";
	}
	Map<String ,Integer> map_success = new HashMap<String ,Integer>();
	private boolean mystatus = true;
	

	@Override
	public synchronized void run() {
		// TODO Auto-generated method stub
		String kqjPostGson;//用于发送的json。
		String sd_id;		//保存读取到的卡号。
		Student showStu = null; //用于显示在UI的学生信息。
		String responsedata = null; 	//用于获取服务器的返回数据。
		StudentDao sd = new StudentDao();//获得本地数据对象。
		RFID rfid_data = new RFID();	//保存考勤完成的数据名单。
		KQJJson kqjJson = new KQJJson();
		KQtime t = new KQtime();
		
		String status_onTime = "0";	//用于上传给服务器判断学生是否考勤状态。准点（0）和迟到（1），缺勤（2）
		int reset = 0;
		int reui = 0;
		while( mystatus ){
			
			//没人刷卡，隔两秒，重置UI；
			if((t.resetUI_Time() - reset) > 2 && reui == 0){
				kqjUI.resetInfo();
				kqjUI.set_notice("欢迎你。");
				reui = 1;
			}
			//考勤过程。
			if(rRFID.readRequest()){
				sd_id = rRFID.getRFID();
				if(sd_id == null){
					continue;
				}
				//匹配三连。
				//[2] 如果存在本地数据，就直接更新UI。
				try {
					showStu = sd.getStudentById(sd_id);
					if(showStu != null){
						kqjUI.setstu_data(showStu.getIcon(), showStu.getName(), showStu.getId());
						kqjUI.set_status_kq(" ");
					}else if(mode.equals("local")&&showStu == null){
						kqjUI.set_status_kq(" ");
						kqjUI.set_notice("你不能考勤！");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//[3] 匹配考勤时间段名单。 防止重复考勤。
				if(isReCheck(sd_id)){
					kqjUI.set_notice("你已经考勤！");
					continue;
				}
				//判断是否迟到。
				if(kqjTime.isOnTime()){
					status_onTime = "0";
				}else{
					status_onTime = "1";
				}
				
				if(mode.equals("net")){
					
					//[1] 不在服务器下发名单不能考勤。
					Integer integer = 0;
					integer = rfid_list.get(sd_id);
					if(integer == null){
						kqjUI.set_notice("你不能考勤!");
						continue;
					}
					
					//本地匹配完成。把考勤学生上传
						//准备好json数据。
					rfid_data.setRfid(sd_id);
					kqjPostGson = kqjJson.parseJson(rfid_data);
					
					//实现UI更新和上发数据；
					try {
//						responsedata = http.deal_http(2, kqjPostGson);
						
						responsedata = http.deal_http(2, sd_id);
						System.out.println("respon = "+responsedata);
						if(responsedata.equals("1")){
							//考勤完成
							kqjUI.set_status_kq("考勤成功！");
							checkSuccess(sd_id);
							
							Trace.print("map_success="+map_success);
						}else{
							kqjUI.set_status_kq("考勤失败！");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					kqjUI.set_status_kq("考勤成功！");
					checkSuccess(sd_id);
					}
					reset = t.resetUI_Time();
					reui = 0;
				}
			if(!kqjTime.isOnTime()){
				kqjUI.set_notice("你已经迟到！");
			}
			}
		System.out.println("考勤结束！");
		}

	
	
	public void checkSuccess(String rfid){
		map_success.put(rfid,1);
	}
	
	public void clearMap(){
		map_success.clear();
	}
	public Map<String ,Integer> ListToMap(List<RFID> stu){
		Map<String ,Integer> m = new HashMap<String ,Integer>();
		
		for(RFID s : stu){
			m.put(s.getRfid(), 1);
		}
		return m;
	}
	
	public boolean isReCheck(String rfid){
		if(map_success.get(rfid) == null ){
			return false;
		}else{
			return true;
		}
		
	}
	
	public Map<String, Integer> getRfid_list() {
		return rfid_list;
	}

	public void setRfid_list(Map<String, Integer> rfid_list) {
		this.rfid_list = rfid_list;
	}

	public void setMode(MyHttp http){
		this.http = http;
		this.mode = "net";
	}
	
	public void mystop() {
		// TODO Auto-generated method stub
		this.mystatus = false;
		
	}

}
