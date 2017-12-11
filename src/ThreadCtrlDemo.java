import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import dbutils.Trace;


/**
 * 线程控制
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
      
	// 判断是否停止
	   mystatus = false;
      while (mystatus )
      {
         // 判断是否挂起
         if (status == SUSPEND)
         {
            try
            {
               // 若线程挂起则阻塞自己
               wait();
            }
            catch (InterruptedException e)
            {
               System.out.println("线程异常终止...");
            }
         }
         else
         {
            count++;
            System.out.println(this.getName() + "第" + count + "次运行...");
            try
            {
               Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
               System.out.println("线程异常终止...");
            }
         }
      }
      System.out.println("@@"+name);
      count = 0;
   }

   /**
    * 恢复
    */
   public synchronized void myResume()
   {
      // 修改状态
      status = RUNNING;
      // 唤醒
      notify();
   }

   /**
    * 挂起
    */
   public void mySuspend()
   {
      // 修改状态
      status = SUSPEND;
   }

   /**
    * 停止
    */
   public void myStop()
   {
      // 修改状态
      status = STOP;
   }

   
}
