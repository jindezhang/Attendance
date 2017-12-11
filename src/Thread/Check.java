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
	  ReadRFID rRFID;//��ȡ���Ŷ���
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
		String kqjPostGson;//���ڷ��͵�json��
		String sd_id;		//�����ȡ���Ŀ��š�
		Student showStu = null; //������ʾ��UI��ѧ����Ϣ��
		String responsedata = null; 	//���ڻ�ȡ�������ķ������ݡ�
		StudentDao sd = new StudentDao();//��ñ������ݶ���
		RFID rfid_data = new RFID();	//���濼����ɵ�����������
		KQJJson kqjJson = new KQJJson();
		KQtime t = new KQtime();
		
		String status_onTime = "0";	//�����ϴ����������ж�ѧ���Ƿ���״̬��׼�㣨0���ͳٵ���1����ȱ�ڣ�2��
		int reset = 0;
		int reui = 0;
		while( mystatus ){
			
			//û��ˢ���������룬����UI��
			if((t.resetUI_Time() - reset) > 2 && reui == 0){
				kqjUI.resetInfo();
				kqjUI.set_notice("��ӭ�㡣");
				reui = 1;
			}
			//���ڹ��̡�
			if(rRFID.readRequest()){
				sd_id = rRFID.getRFID();
				if(sd_id == null){
					continue;
				}
				//ƥ��������
				//[2] ������ڱ������ݣ���ֱ�Ӹ���UI��
				try {
					showStu = sd.getStudentById(sd_id);
					if(showStu != null){
						kqjUI.setstu_data(showStu.getIcon(), showStu.getName(), showStu.getId());
						kqjUI.set_status_kq(" ");
					}else if(mode.equals("local")&&showStu == null){
						kqjUI.set_status_kq(" ");
						kqjUI.set_notice("�㲻�ܿ��ڣ�");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//[3] ƥ�俼��ʱ��������� ��ֹ�ظ����ڡ�
				if(isReCheck(sd_id)){
					kqjUI.set_notice("���Ѿ����ڣ�");
					continue;
				}
				//�ж��Ƿ�ٵ���
				if(kqjTime.isOnTime()){
					status_onTime = "0";
				}else{
					status_onTime = "1";
				}
				
				if(mode.equals("net")){
					
					//[1] ���ڷ������·��������ܿ��ڡ�
					Integer integer = 0;
					integer = rfid_list.get(sd_id);
					if(integer == null){
						kqjUI.set_notice("�㲻�ܿ���!");
						continue;
					}
					
					//����ƥ����ɡ��ѿ���ѧ���ϴ�
						//׼����json���ݡ�
					rfid_data.setRfid(sd_id);
					kqjPostGson = kqjJson.parseJson(rfid_data);
					
					//ʵ��UI���º��Ϸ����ݣ�
					try {
//						responsedata = http.deal_http(2, kqjPostGson);
						
						responsedata = http.deal_http(2, sd_id);
						System.out.println("respon = "+responsedata);
						if(responsedata.equals("1")){
							//�������
							kqjUI.set_status_kq("���ڳɹ���");
							checkSuccess(sd_id);
							
							Trace.print("map_success="+map_success);
						}else{
							kqjUI.set_status_kq("����ʧ�ܣ�");
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					kqjUI.set_status_kq("���ڳɹ���");
					checkSuccess(sd_id);
					}
					reset = t.resetUI_Time();
					reui = 0;
				}
			if(!kqjTime.isOnTime()){
				kqjUI.set_notice("���Ѿ��ٵ���");
			}
			}
		System.out.println("���ڽ�����");
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
