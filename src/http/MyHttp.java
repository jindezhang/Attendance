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
		
		conn.setRequestMethod("POST");  //�൱��: method=POST
		conn.setDoOutput( true );       //�ύ���Ĳ��� ---> true
		
		conn.setRequestProperty("Connection", "Keep-Alive");  
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestProperty("Content-Type",
			"application/x-www-form-urlencoded");
		sendData( conn );
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
		//������һ���ַ���: name=andy&password=123&sex=��&
		if( count>0 ){
			sb.setLength(sb.length()-1);
		}
		//�ص����һ�� "&"
		//���: name=andy&password=123&sex=��
		out.write( sb.toString().getBytes() );
		out.flush();
		out.close();
	}
	
	//�ӷ������ϻ�ȡ���ص�����
	public String getResponse() throws IOException{
		//״̬��: 200
		int code = conn.getResponseCode();   //��ȡ���������ص�״̬��
		InputStream in = null;
		if( code==HttpURLConnection.HTTP_OK ){
			in = conn.getInputStream();
			BufferedReader read = new BufferedReader(
					new InputStreamReader(in,"GB2312") );
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
		
		addParameter("data", post_data);
		
		doConnect();    //�����Ӳ���
		
		String line = getResponse();  //�÷��ص�����
		System.out.println( line );
		return line;
		
	}
	
	public String deal_http(int commands,String post_data) throws Exception{
		
		String[] command = {"studentall#","rfid#sx101#","send#sx101#"};
		StringBuffer sb = new StringBuffer(command[commands]);
		sb.append(post_data);
		addParameter("data", sb.toString());
		doConnect();    //�����Ӳ���
		String line = getResponse();  //�÷��ص�����
		System.out.println( line );
		return line;
		
	}
	public static void main(String[] args) throws Exception {
		String url1 = "http://172.16.13.246:8080/MvcTest3/Attendance/sumbit";
//		String url2 = "http://172.16.13.246:8080/MvcTest3/Attendance/sumbit";

		MyHttp http = new MyHttp();
		http.openConnection( url1 );
		String[] command = {"studentall#","rfid#","send#"};
		StringBuffer sb = new StringBuffer(command[1]);
		String json =  "{\"name\":\"andy\",\"pass\":\"123\"}";
		sb.append(json);
		
		http.addParameter("data",sb.toString());
		
		http.doConnect();    //�����Ӳ���
		
		String line = http.getResponse();  //�÷��ص�����
		
		System.out.println( line );
		
	}
}






