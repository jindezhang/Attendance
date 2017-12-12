package http;

import java.io.*;
import java.net.*;


public class DownloadFile {
	/**
     * TODO �����ļ�������
     * @author nadim  
     * @date Sep 11, 2015 11:45:31 AM
     * @param fileUrl Զ�̵�ַ
     * @param fileLocal ����·��
     * @throws Exception 
     */
    public void downloadFile(String fileUrl,String fileLocal) throws Exception {
        URL url = new URL(fileUrl);
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        urlCon.setConnectTimeout(6000);
        urlCon.setReadTimeout(6000);
        int code = urlCon.getResponseCode();
        if (code != HttpURLConnection.HTTP_OK) {
            throw new Exception("�ļ���ȡʧ��");
        }
        
        //���ļ���
        DataInputStream in = new DataInputStream(urlCon.getInputStream());
        DataOutputStream out = new DataOutputStream(new FileOutputStream(fileLocal));
        byte[] buffer = new byte[2048];
        int count = 0;
        while ((count = in.read(buffer)) > 0) {
            out.write(buffer, 0, count);
        }
        out.close();
        in.close();
    }
    
    public static void main(String[] args) throws Exception {
		DownloadFile df = new DownloadFile();
		
		String fileUrl = "http://img0.imgtn.bdimg.com/it/u=160554372,288864420&fm=27&gp=0.jpg";

		String fileLocal = "src\\pic";
		df.downloadFile(fileUrl , fileLocal);
	}
}
