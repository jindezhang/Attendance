import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import dbutils.Trace;


/**
 * �߳̿���
 * 
 * @author jianggujin
 *
 */
public class ThreadCtrlDemo extends Thread
{
   private final int STOP = -1;
   private final int SUSPEND = 0;
   private final int RUNNING = 1;
   private int status = 1;
   private long count = 0;
   boolean mystatus = true;
   String name;
   public void setstatus(boolean mystatus){
	   this.mystatus = mystatus;
   }
   
   public ThreadCtrlDemo(String name)
   {
	   
      super(name);
      this.name = name;
   }

   @Override
   public synchronized void run()
   {
      
	// �ж��Ƿ�ֹͣ
	   mystatus = false;
      while (mystatus )
      {
         // �ж��Ƿ����
         if (status == SUSPEND)
         {
            try
            {
               // ���̹߳����������Լ�
               wait();
            }
            catch (InterruptedException e)
            {
               System.out.println("�߳��쳣��ֹ...");
            }
         }
         else
         {
            count++;
            System.out.println(this.getName() + "��" + count + "������...");
            try
            {
               Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
               System.out.println("�߳��쳣��ֹ...");
            }
         }
      }
      System.out.println("@@"+name);
      count = 0;
   }

   /**
    * �ָ�
    */
   public synchronized void myResume()
   {
      // �޸�״̬
      status = RUNNING;
      // ����
      notify();
   }

   /**
    * ����
    */
   public void mySuspend()
   {
      // �޸�״̬
      status = SUSPEND;
   }

   /**
    * ֹͣ
    */
   public void myStop()
   {
      // �޸�״̬
      status = STOP;
   }

   
}
