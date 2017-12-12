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
 * timeH:存放上课的时间的时；
 * timeM：存放上课的时间的分；
 * 在数组中，下标一一对应，组成一个完整的上课时间点。
 */
public class KQtime {
	int[] timeH;
	int[] timeM;
	int kqTimeLong;
	
	/*
	 * 不带参数构造方法：默认设置。
	 * 带参数的构造方法：可以设置多节课的上课时间点。
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
	 * 设置时间长度，默认调用时间点。
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
	 * 上课前（kqTimeLong/2）+ 2 分钟申请名单。
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
	 * 上课后kqTimeLong/2分钟不能考勤。
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
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改   
		int[] time = new int[2];
        time[0] = c.get(Calendar.HOUR_OF_DAY);   
        time[1] = c.get(Calendar.MINUTE);    
        return time;
	}
	
	/*
	 * 作用：考勤机是否允许向服务器请求名单数据？
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
	 * 用于判断是否准点。
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
	//比系统时间小，返回真。
	private boolean compareOSTime(int[] ost, int[] myt){
		/*
		 * 准点：7:50  早到：7：40 迟到：8：10
		 * [1] (7-7)*60 + 40 -50
		 * [2] (8-7)*60 + 10 -50
		 * 准点：7:40  早到：7：30 迟到：7：50
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
	 * 作用：是否关闭考勤机考勤功能
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
	 * 作用：是否开启考勤功能？
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
	
	//n表示设置第几节课的时间。
	public void setTime(int n) throws NumberFormatException, IOException{
		showTime();
		if(n > timeH.length){
			System.out.println("输入有误！");
			return;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请输入小时：");
		timeH[n] = Integer.parseInt(br.readLine());
		System.out.println("请输入分钟：");
		timeM[n] =Integer.parseInt( br.readLine());
		System.out.println("第"+n+"节课新上课时间为："+":"+timeH[n]+":"+timeM[n]);
		
		System.out.println("设置成功");
	}
	
	/*
	 * 获取考勤时间点，时和分在int[]中一一对应。上课前(kqTimeLong/2)开始考勤
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
	 * 显示当前所有上课时间点。
	 */
	public void showTime(){
		System.out.println("上课时间点有：");
		for(int i = 0; i<timeH.length;i++){
			System.out.println("第"+(i+1)+"节课:"+timeH[i]+":"+timeM[i]+"\t考勤时间："+
		(timeH[i])+":"+(timeM[i] - (kqTimeLong/2)));
		}
		System.out.println();
	}
	

	/*
	 * 用于判断考勤期间是否重置ui
	 */
	public int resetUI_Time(){
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改      
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
        
        System.out.println("当前时间："+hh+":"+mm);
        KQtime kqtime = new KQtime();
        kqtime.showTime();
        int[] g = kqtime.getOSTime();
        if(kqtime.isOpenKQJ())
        	System.out.println("$$");
        
        int[] h = {1,2};
        int[] m = {3,4};
        kqtime.setOnTime(2, h, m);
        	
        System.out.println("g[0]="+g[0]);
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改   
        int year = c.get(Calendar.YEAR);  
        int month = c.get(Calendar.MONTH);   
        int date = c.get(Calendar.DATE);    
        int hour = c.get(Calendar.HOUR_OF_DAY);   
        int minute = c.get(Calendar.MINUTE);   
        int second = c.get(Calendar.SECOND);    
        System.out.println(year + "/" + month + "/" + date + " " +hour + ":" +minute + ":" + second);  
	}

}
