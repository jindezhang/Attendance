package Thread;
import http.MyHttp;

import java.util.HashMap;
import java.util.Map;

import UI.KQGui;
import controll.KQJJson;
import controll.KQtime;
import controll.ProTest;
import controll.ReadRFID;
import dbutils.Trace;


public class KQJMain {
	
	public void start() throws Exception{
		KQGui kqjUI = new KQGui();
		KQtime kqjTime =new KQtime();
		KQJJson kqjJson = new KQJJson();
		Map<String, Integer> rfid_list = new HashMap<String,Integer>();
		MyHttp http = new MyHttp(); 
		ReadRFID rRFID = new ReadRFID();
		ProTest proTest = new ProTest();
		
		String[] paras;
		Check check = null;
		String kqjURL = null;
		
		/*
		 * 检查刷卡设备。
		 */
		kqjUI.set_notice("设备故障！请重启。");
		if( !rRFID.init() ){
			System.out.println("考勤机启动失败。");
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			kqjUI.set_notice("欢迎您");
			System.out.println("考勤机启动成功。");
			
		}

		/*
		 * 读取配置。失败则跳转到手动写入配置信息。并且注册考勤机。
		 * serverurl = paras[0]
		 * workMode = paras[1]
		 */
		boolean flet = false;
		paras = proTest.readParas();
		do{
				int[] h = proTest.getProH();
				int[] m = proTest.getProM();
//				kqjTime.setOnTime(h.length, h, m);
		}while(flet);
		if(paras[1].equals("local")){
			kqjURL = null;
			kqjUI.setMode("本地考勤");
		}else{
			kqjURL = paras[0];
			http.openConnection(kqjURL);
			kqjUI.setMode("联网考勤");
		}
		kqjTime.showTime();

		/*
		 * 进入考勤功能模块。
		 */
		String getJson = null;
		while(true){
			
			check =new Check(kqjUI,rRFID);
			if(kqjURL != null){
				//申请学生名单：
				while(!kqjTime.isGetdata()); 
				getJson = http.deal_http(1,null);
				Trace.print(getJson);
				
				if(getJson == null){
					System.out.println("服务器没有下发学生名单");
					Thread.sleep(62*1000);
					continue;
				}
				
				//清空原有数据。
				rfid_list.clear();
				//保存学生数据。
				rfid_list = check.ListToMap(kqjJson.getdata(getJson).getRfid_list());
				check.setRfid_list(rfid_list);
				
				check.setMode(http);;
			}

			System.out.println("##");
			//是否开始考勤
			while(!kqjTime.isOpenKQJ());
			kqjUI.check_Time();
			check.start();
			
			//是否关闭考勤
			while(!kqjTime.isCloseKQJ());
			check.mystop();
			kqjUI.nocheck_Time();
			kqjUI.resetInfo();
			
		}
		
	}
	
	

		
}
