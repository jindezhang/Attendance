import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import dbutils.Trace;


public class ThreadTest {

	@SuppressWarnings("deprecation")
	public static void main(String[] args)
	   {
		String[] s = new String[2];
		s[1] = "1";
		s[0] = "0";
		Trace.print(s);
		ThreadCtrlDemo demow = null;
		for(int i = 0 ;i<3;i++){
			demow = new ThreadCtrlDemo("�����߳�"+i);
			demow.start();
		}
		
		Map<String, String> rfid_list = new HashMap<String,String>();
		
	      ThreadCtrlDemo demo = new ThreadCtrlDemo("�����߳�");
	      demo.start();
	      try
	      {
	         Thread.sleep(2000);
	      }
	      catch (InterruptedException e)
	      {
	         System.out.println("���߳��쳣��ֹ...");
	      }
	     
	      System.out.println("@@");
	      System.out.println("�����̼߳���������...");
	      demo.setstatus(false);
	      try
	      {
	         Thread.sleep(1000);
	      }
	      catch (InterruptedException e)
	      {
	         System.out.println("���߳��쳣��ֹ...");
	      }
	      System.out.println("�����̼߳���������...");
	      ThreadCtrlDemo demoo = new ThreadCtrlDemo("�����߳�");
	      demoo.start();
	      try
	      {
	         Thread.sleep(1000);
	      }
	      catch (InterruptedException e)
	      {
	         System.out.println("���߳��쳣��ֹ...");
	      }
	      System.out.println("��ֹ�����߳�...");
	      demoo.setstatus(false);
	      System.out.println("���߳���ֹ...");
	   }
}
