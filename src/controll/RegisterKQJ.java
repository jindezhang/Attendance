package controll;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import http.MyHttp;

public class RegisterKQJ {

	MyHttp http = new MyHttp();
	String configPath;
	
	public RegisterKQJ(String configPath){
		this.configPath = configPath;
	}
	
	public void myregister() throws Exception{
		Map<String,String> paras = new HashMap<String,String>();
		ProTest pro = new ProTest();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("请输入服务器通信地址 ：");
		String url = br.readLine();
		String s = "NMBB2427#"+InetAddress.getLocalHost().getHostAddress()+"#9090";
		
		http.openConnection(url);
		String respon = http.deal_http(0, s);
//		System.out.println("respon"+respon);
		String[] config = respon.split("\\*");
		
		paras.put("serverUrl", config[0]);
		paras.put("serverIp", config[1]);
		paras.put("workMode", config[2]);
		paras.put("time", config[3]);
		paras.put("timelong", config[4]);
		paras.put("serverpost", config[5]);
		pro.writeParas(paras);	
	}
	
	public static void main(String[] args) {
		RegisterKQJ r = new RegisterKQJ("src\\config.properties");
		try {
			r.myregister();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
