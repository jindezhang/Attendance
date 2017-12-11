package controll;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.junit.Test;

public class ProTest {
/*
	serverIp=undefined     [ 出厂设置 ]
	serverPort=undefined
	attUpload=undefined
	regStatus=undefined
	workMode=undefined
	termStart=undefined
*/	
	String path = "src\\config.properties";
	String time;
	public String[] readParas() throws IOException {
		String[] parasInfo = new String[6];
		InputStream fin = new FileInputStream( path );
		Properties pros = new Properties();
		pros.load( fin );		
		String serverIP = pros.getProperty("serverIp");
		String serverPort = pros.getProperty( "serverPort" );
		String serverUrl = pros.getProperty("serverUrl");
		String workMode = pros.getProperty("workMode");
		time = pros.getProperty("time");
		
		parasInfo[0] = serverUrl;
		parasInfo[1] = workMode;
		parasInfo[2] = time;
		
		System.out.println("serverIP="+ serverIP);
		System.out.println("serverPort="+ serverPort);
		System.out.println("time="+ time);		
		fin.close();
		return parasInfo;
	}
	
	/*
	 * 把读取到的时间字符串转为时间int[];
	 */
	public int[] getProH(){
		String[] sh;
		String[] times = time.split("#");	
		int[] h = new int[times.length];
		for(int i = 0; h.length > i; i++){
			sh = times[i].split(":");
			h[i] =  Integer.parseInt(sh[0], 10);
		}
		return h;
	}
	public int[] getProM(){
		String[] sh;
		String[] times = time.split("#");	
		int[] h = new int[times.length];
		for(int i = 0; h.length > i; i++){
			sh = times[i].split(":");
			h[i] =  Integer.parseInt(sh[1], 10);
		}
		return h;
	}
	
	/*
	 * 把时间int[] 转为对应的时间字符串配置文件。
	 */
	public String setProtime(int[] h, int[] m){
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < h.length; i++){
			sb.append(h[i]);
			sb.append(":");
			sb.append(m[i]);
			sb.append("#");
		}
		return sb.toString();
	}
	
	public void writeParas(Map<String,String> map)
			throws IOException {
		OutputStream fout = new FileOutputStream( path );
		Properties pros = new Properties();
		Set<Entry<String, String>> set = map.entrySet();
		for( Entry<String, String> entry : set ){
			String key = entry.getKey();
			String value = entry.getValue();
			pros.setProperty( key, value );
		}
		pros.store( fout, "2017-12-05" );   //保存数据到文件
		fout.close();   //关闭输出流
	}
	
	
	
	@Test
	public void test(){
		String path = "src\\config\\config.properties";
		Map<String,String> paras = new HashMap<String,String>();
		paras.put("serverIp", "192.168.1.100");
		paras.put("serverPort", "9090");
		paras.put("attUpload", "undefined");
		paras.put("regStatus", "ok");
		paras.put("workMode", "local");
		paras.put("termStart", "0");
		paras.put("serverUrl", "0");
		int[] hh = {10,12};
		int[] m = {30,40};
		String s = setProtime(hh, m);
		paras.put("time", s);
		try {
			writeParas( paras );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
//	public void test(){
//		
//		try {
//			readParas();
//			System.out.println("$$");
//			int[] h = getProM();
//			for(int i = 0;i < h.length;i++){
//				System.out.println("time = "+h[i]);
//			}
//			int[] hh = {10,12};
//			int[] m = {30,40};
//			String s = setProtime(hh, m);
//			System.out.println("s:"+s);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	
	
	
}
