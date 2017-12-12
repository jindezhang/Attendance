package Thread;
import http.MyHttp;

import java.util.HashMap;
import java.util.Map;

import UI.KQGui;
import controll.GetStudent;
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
		 * ���ˢ���豸��
		 */
		kqjUI.set_notice("�豸���ϣ���������");
		if( !rRFID.init() ){
			System.out.println("���ڻ�����ʧ�ܡ�");
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			kqjUI.set_notice("��ӭ��");
			System.out.println("���ڻ������ɹ���");
			
		}

		/*
		 * ��ȡ���á�ʧ������ת���ֶ�д��������Ϣ����������ģʽ������ע�ῼ�ڻ��ͻ�ȡȫ��ѧ�����ݡ���
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
		if(paras[1].equals("net")){
			kqjURL = paras[0];
			http.openConnection(kqjURL);
			System.out.println("kqjURL:"+kqjURL);
			kqjUI.setMode("��������");
//			GetStudent getStudent = new GetStudent(http);
//			getStudent.saveStudentAll();
			
			 
		}else{
			kqjURL = null;
			kqjUI.setMode("���ؿ���");
		}
		kqjUI.set_notice("��ʼ���ɹ���");
		kqjTime.showTime();

		/*
		 * ���뿼�ڹ���ģ�顣
		 */
		String getJson = null;
		while(true){
			
			
			if(kqjURL != null){
				//����ѧ��������
				while(!kqjTime.isGetdata()); 
				getJson = http.deal_http(2,null);
				Trace.print(getJson);
				
				if(getJson == null){
					System.out.println("������û���·�ѧ������");
					Thread.sleep(62*1000);
					continue;
				}
			}

			System.out.println("##");
			//�Ƿ�ʼ����
			while(!kqjTime.isOpenKQJ());
			check =new Check(kqjUI,rRFID,kqjTime);
			
			if(kqjURL != null){
				//���ԭ�����ݡ�
				rfid_list.clear();
				//����ѧ�����ݡ�
				rfid_list = check.ListToMap(kqjJson.getdata(getJson).getRfid_list());
				check.setRfid_list(rfid_list);
				check.setCourseId(kqjJson.getdata(getJson).getCourseId());
				
				check.setMode(http);;
			}
			
			kqjUI.check_Time();
			check.start();
			
			//�Ƿ�رտ���
			while(!kqjTime.isCloseKQJ());
			check.mystop();
			kqjUI.nocheck_Time();
			kqjUI.resetInfo();
			
		}
		
	}
	
	

		
}
