package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MyHttp {
	URL url = null;
	HttpURLConnection conn = null;
	Map<String,String> paras = new HashMap<String,String>();
	
	public void openConnection(String _url) 
			throws Exception {
		url = new URL( _url );
	}
	public void addParameter(String key,String value){
		paras.put(key, value);
	}
	public void doConnect() throws IOException{
		//URLConnection 
		conn = (HttpURLConnection)url.openConnection();
		
		conn.setRequestMethod("POST");  //相当于: method=POST
		conn.setDoOutput( true );       //提交表单的参数 ---> true
		
		conn.setRequestProperty("Connection", "Keep-Alive");  
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestProperty("Content-Type",
			"application/x-www-form-urlencoded");
//		conn.setRequestProperty("Content-Type",
//				"application/json");
//		sendData( conn );
	}
	
	public void doMyConnect() throws IOException{
		//URLConnection 
		conn = (HttpURLConnection)url.openConnection();
		
		conn.setRequestMethod("GET");  //相当于: method=POST
		conn.setDoOutput( true );       //提交表单的参数 ---> true
		
		conn.setRequestProperty("Connection", "Keep-Alive");  
		conn.setRequestProperty("Charset", "UTF-8");
		
		conn.setRequestProperty("Content-Type",
				"application/json");
//		sendData( conn );
	}
	
	private void sendData( HttpURLConnection conn )
			throws IOException{
		OutputStream out = conn.getOutputStream();
		Set<String> keys = paras.keySet();
		Iterator<String> it = keys.iterator();
		StringBuffer sb = new StringBuffer();
		int count = 0;
		while( it.hasNext() ){
			count ++;
			String key = it.next();
			String value = paras.get(key);
			sb.append( key+"="+value +"&" );
		}
		//生成这一条字符串: name=andy&password=123&sex=男&
		if( count>0 ){
			sb.setLength(sb.length()-1);
		}
		//截掉最后一个 "&"
		//变成: name=andy&password=123&sex=男
		out.write( sb.toString().getBytes() );
		out.flush();
		out.close();
	}
	
	private void sendMyData( HttpURLConnection conn, String postdata )
			throws IOException{
		OutputStream out = conn.getOutputStream();
		
		out.write(postdata.getBytes());
		out.flush();
		out.close();
	}
	
	//从服务器上获取返回的数据
	public String getResponse() throws IOException{
		//状态码: 200
		int code = conn.getResponseCode();   //获取服务器返回的状态码
		InputStream in = null;
		System.out.println("code:"+code);
		if( code==HttpURLConnection.HTTP_OK ){
			in = conn.getInputStream();
			BufferedReader read = new BufferedReader(
					new InputStreamReader(in,"UTF-8") );
//					new InputStreamReader(in,"GB2312") );
			
			StringBuffer sb = new StringBuffer();
			String line = "";
			while( line!=null ){
				line = read.readLine();
				if( line!=null ){
					sb.append( line );
				}
			}
			return sb.toString();
		}
		return null;
	}
	
	public String deal_http(String post_data) throws Exception{
		
		
//		String url = "http://172.16.13.246:8080/%E6%A0%A1%E5%86%85%E5%AE%9E%E8%AE%AD17-11-27/text/666.json";
		
		doMyConnect();    //做连接操作
		sendMyData( conn ,post_data);
		String line = getResponse();  //拿返回的数据
		System.out.println( line );
		return line;
		
	}
	
	public String deal_http(int commands,String post_data) throws Exception{
		
		String[] command = {"register#","studentAll#sx101#","rfid#sx101#","send#sx101#"};
		StringBuffer sb = new StringBuffer(command[commands]);
		sb.append(post_data);
		addParameter("data", sb.toString());
		doConnect();    //做连接操作
		sendData( conn );
		String line = getResponse();  //拿返回的数据
		System.out.println( line );
		return line;
		
	}
	public static void main(String[] args) throws Exception {
		MyHttp http = new MyHttp();
		String url2 = "http://rj1033/api/v1/attendances/";
//		String url2 = "http://172.16.15.185:8080/MvcTest3/Attendance/sumbit";
//		String url2 = "http://www.sojson.com/open/api/weather/json.shtml?city=%E5%8C%97%E4%BA%AC";
		
		String url = "http://www.sojson.com/open/api/weather/json.shtml?city=%E5%8C%97%E4%BA%AC";
		http.openConnection( url2 );
		
		String s = "{\"id\": 1,\"rfid\": \"123456790\",\"squadId\": \"sqd14rj01\",\"lessonId\": 1,\"state\": 20}";
		
		
		http.deal_http(s);
		
		String line = http.getResponse();  //拿返回的数据
		System.out.println( line );
		System.out.println("");
		
	}
}






