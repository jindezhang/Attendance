package controll;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dbutils.Trace;

/*
 * timeH:����Ͽε�ʱ���ʱ��
 * timeM������Ͽε�ʱ��ķ֣�
 * �������У��±�һһ��Ӧ�����һ���������Ͽ�ʱ��㡣
 */
public class KQtime {
	int[] timeH;
	int[] timeM;
	int kqTimeLong;
	
	/*
	 * �����������췽����Ĭ�����á�
	 * �������Ĺ��췽�����������ö�ڿε��Ͽ�ʱ��㡣
	 */
	public KQtime() {
		// TODO Auto-generated constructor stub
		timeH = new int[2];
		timeM = new int[2];
		
		this.timeH[0] = 16;
		this.timeH[1] = 17;
		
		this.timeM[0] = 55;
		this.timeM[1] = 34;
		
		kqTimeLong = 2;
	}
	/*
	 * ����ʱ�䳤�ȣ�Ĭ�ϵ���ʱ��㡣
	 */
	public KQtime(int kqTimeLong) {
		this.kqTimeLong = kqTimeLong;
		
		timeH = new int[2];
		timeM = new int[2];
		this.timeH[0] = 16;
		this.timeH[1] = 12;
		this.timeM[0] = 44;
		this.timeM[1] = 00;
	}
	
	
	public void setOnTime(int n,int[] h, int[] m) throws IOException{
		timeH =new int[n];
		timeM =new int[n];
		timeH = h;
		timeM = m;
		System.out.println("h1:"+timeH[1]+",h0:"+timeH[0]);
	}
	
	/*
	 * �Ͽ�ǰ��kqTimeLong/2��+ 2 ��������������
	 */
	private Map<Integer, Integer> getDataTime(){
		int[] gettimeH = new int[timeH.length];
		System.arraycopy(timeH, 0, gettimeH, 0, timeH.length);
		int[] gettimeM = new int[timeH.length];
		System.arraycopy(timeM, 0, gettimeM, 0, timeH.length);
		int hh,mm;
		Map<Integer, Integer> list = new HashMap<Integer, Integer>();
		
		for(int i = 0 ;i <gettimeH.length;i++){
			mm = gettimeM[i] - (kqTimeLong/2)-2;
			if(mm < 0){
				hh = gettimeH[i]-1;
				mm = mm+60;
			}
			hh = gettimeH[i];
			list.put(hh, mm);
		}
		return list;	
	}
	
	/*
	 * �Ͽκ�kqTimeLong/2���Ӳ��ܿ��ڡ�
	 */
	private Map<Integer, Integer> CloseKQJTime(){
		int[] gettimeH = new int[timeH.length];
		System.arraycopy(timeH, 0, gettimeH, 0, timeH.length);
		int[] gettimeM = new int[timeH.length];
		System.arraycopy(timeM, 0, gettimeM, 0, timeH.length);
		int hh,mm;
		Map<Integer, Integer> list = new HashMap<Integer, Integer>();
		for(int i = 0 ;i <gettimeH.length;i++){
			mm = gettimeM[i] + (kqTimeLong/2);
			if(mm > 60){
				hh = gettimeH[i]+1;
				mm = mm-60;
			}
			hh = gettimeH[i];
			list.put(hh, mm);
		}
		return list;	
	}
	
	private int[] getOSTime(){
		Calendar c = Calendar.getInstance();//���Զ�ÿ��ʱ���򵥶��޸�   
		int[] time = new int[2];
        time[0] = c.get(Calendar.HOUR_OF_DAY);   
        time[1] = c.get(Calendar.MINUTE);    
        return time;
	}
	
	/*
	 * ���ã����ڻ��Ƿ�����������������������ݣ�
	 */
	public boolean isGetdata(){
		Map<Integer, Integer> timeGetData = getKQTime();
//		Map<Integer, Integer> timeGetData = getDataTime();
		int[] timeOS = getOSTime();
		int c = -1;
		for(int i = 0 ;i < timeGetData.size();i++){
			if(timeGetData.get(timeOS[0]) != null){
				c = timeGetData.get(timeOS[0]);
			}
			if( c == timeOS[1] )
				return true;
		}
  
		return false;
	}

	/*
	 * �����ж��Ƿ�׼�㡣
	 */
	public boolean isOnTime(){
		int[] osT = getOSTime();
		int[] t = new int[2];
		for(int i = 0 ; i<timeH.length;i++){
			t[0] = timeH[i];
			t[1] = timeM[i];
			if(compareOSTime(osT, t ))
				return true;
		}
		return false;
		
	}
	//��ϵͳʱ��С�������档
	private boolean compareOSTime(int[] ost, int[] myt){
		/*
		 * ׼�㣺7:50  �絽��7��40 �ٵ���8��10
		 * [1] (7-7)*60 + 40 -50
		 * [2] (8-7)*60 + 10 -50
		 * ׼�㣺7:40  �絽��7��30 �ٵ���7��50
		 * [1] 30 -40
		 * [2] 50 -40
		 */
		int vh = ost[0] - myt[0];
		int vm = ost[1] + vh*60 -myt[1];
		if(vm <= 0){
			return true;
		}else
			return false;
	}
	/*
	 * ���ã��Ƿ�رտ��ڻ����ڹ���
	 */
	public boolean isCloseKQJ(){
		
		Map<Integer, Integer> timeCloseKQJ = CloseKQJTime();
		int[] timeOS = getOSTime();
		int c = -1;
		for(int i = 0 ;i < timeCloseKQJ.size();i++){
			if(timeCloseKQJ.get(timeOS[0]) != null){
				c = timeCloseKQJ.get(timeOS[0]);
			}
			if( c == timeOS[1] )
				return true;
		}
		return false;
	}
	
	/*
	 * ���ã��Ƿ������ڹ��ܣ�
	 */
	public boolean isOpenKQJ(){
		
		Map<Integer, Integer> timeKQ = getKQTime();
		int c = -1;
		int[] timeOS = getOSTime();
//		System.out.println("timeOS="+timeOS[0]);
//		Trace.print(timeOS);
		
		for(int i = 0 ;i < timeKQ.size();i++){
			if(timeKQ.get(timeOS[0]) != null){
				c = timeKQ.get(timeOS[0]);
			}
				if( c == timeOS[1] )
					return true;
		}
		return false;
	}
	
	//n��ʾ���õڼ��ڿε�ʱ�䡣
	public void setTime(int n) throws NumberFormatException, IOException{
		showTime();
		if(n > timeH.length){
			System.out.println("��������");
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("������Сʱ��");
		timeH[n] = Integer.parseInt(br.readLine());
		System.out.println("��������ӣ�");
		timeM[n] =Integer.parseInt( br.readLine());
		System.out.println("��"+n+"�ڿ����Ͽ�ʱ��Ϊ��"+":"+timeH[n]+":"+timeM[n]);
		
		System.out.println("���óɹ�");
	}
	
	/*
	 * ��ȡ����ʱ��㣬ʱ�ͷ���int[]��һһ��Ӧ���Ͽ�ǰ(kqTimeLong/2)��ʼ����
	 */
	private Map<Integer, Integer> getKQTime(){
		int[] gettimeH = new int[timeH.length];
		System.arraycopy(timeH, 0, gettimeH, 0, timeH.length);
		int[] gettimeM = new int[timeH.length];
		System.arraycopy(timeM, 0, gettimeM, 0, timeH.length);
		int hh,mm;
		Map<Integer, Integer> list = new HashMap<Integer, Integer>();
		
		for(int i = 0 ;i <gettimeH.length;i++){
			mm = gettimeM[i] - (kqTimeLong/2);
			if(mm < 0){
				hh = gettimeH[i]-1;
				mm = mm+60;
			}
			hh = gettimeH[i];
			list.put(hh, mm);
		}
		return list;
	}
	
	/*
	 * ��ʾ��ǰ�����Ͽ�ʱ��㡣
	 */
	public void showTime(){
		System.out.println("�Ͽ�ʱ����У�");
		for(int i = 0; i<timeH.length;i++){
			System.out.println("��"+(i+1)+"�ڿ�:"+timeH[i]+":"+timeM[i]+"\t����ʱ�䣺"+
		(timeH[i])+":"+(timeM[i] - (kqTimeLong/2)));
		}
		System.out.println();
	}
	

	/*
	 * �����жϿ����ڼ��Ƿ�����ui
	 */
	public int resetUI_Time(){
		Calendar c = Calendar.getInstance();//���Զ�ÿ��ʱ���򵥶��޸�      
        int minute = c.get(Calendar.MINUTE);   
        int second = c.get(Calendar.SECOND);
        int time = minute*60+second;
        return time;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		long timemillis = System.currentTimeMillis();  
		SimpleDateFormat dfh = new SimpleDateFormat("HH");  
        String hh = new String(dfh.format(new Date(timemillis)));
        SimpleDateFormat dfm = new SimpleDateFormat("mm");  
        String mm = new String(dfm.format(new Date(timemillis)));
        
        System.out.println("��ǰʱ�䣺"+hh+":"+mm);
        KQtime kqtime = new KQtime();
        kqtime.showTime();
        int[] g = kqtime.getOSTime();
        if(kqtime.isOpenKQJ())
        	System.out.println("$$");
        
        int[] h = {1,2};
        int[] m = {3,4};
        kqtime.setOnTime(2, h, m);
        	
        System.out.println("g[0]="+g[0]);
        Calendar c = Calendar.getInstance();//���Զ�ÿ��ʱ���򵥶��޸�   
        int year = c.get(Calendar.YEAR);  
        int month = c.get(Calendar.MONTH);   
        int date = c.get(Calendar.DATE);    
        int hour = c.get(Calendar.HOUR_OF_DAY);   
        int minute = c.get(Calendar.MINUTE);   
        int second = c.get(Calendar.SECOND);    
        System.out.println(year + "/" + month + "/" + date + " " +hour + ":" +minute + ":" + second);  
	}

}
