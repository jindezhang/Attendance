package UI;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


/*
 * 常用字符串：故障、考勤时间、非考勤时间、考勤成功、考勤失败。
 */
public class UItest extends JFrame {
	
	JLabel stu_pic;
	JLabel stu_name;
	JLabel stu_num;
	JLabel status_kq;
	JLabel status_kqj;
	JLabel notice;
	int width;
	int height;
	int j_size = 20;
	final int KQOPEN = 1;
	final int KQCLOSE = 2;
	final int KQSUCESS = 3;
	final int KQLOSE = 4;
	final int KQERROE = 0;
	String default_url = "src\\1.jpg";
	String[] stutas = {"故障!!!","考勤时间","非考勤时间!","考勤成功！","考勤失败。"};
	private JLabel status_mode;
	
	private JPanel BGImage = new JPanel() {  
		  
        protected void paintComponent(Graphics g) { 
        	String url = "src\\bg.jpg";
        	
            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage();  
            g.drawImage(img, 0, 0, icon.getIconWidth(),  
                    icon.getIconHeight(), icon.getImageObserver());  
//            jframe.setSize(icon.getIconWidth(), icon.getIconHeight());  

        }  
	};
	public UItest() {
		// TODO Auto-generated constructor stub
		
		width = 160;
		height = 190;
		setSize(620,370);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		getContentPane().setLayout(null);
		setTitle("考勤机");
		
		BGImage.setBounds(0, 0, 620, 370);
		getContentPane().add(BGImage);
		
		JLabel lblNewLabel = new JLabel("姓名：");
		lblNewLabel.setFont(new Font("Dialog",1,20));
		lblNewLabel.setBounds(300, 80, 100, 50);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("学号：");
		lblNewLabel_1.setFont(new Font("Dialog",1,20));
		lblNewLabel_1.setBounds(300, 125, 100, 50);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("考勤状态：");
		lblNewLabel_2.setFont(new Font("Dialog",1,20));
		lblNewLabel_2.setBounds(300, 185, 200, 25);
		getContentPane().add(lblNewLabel_2);
		
		
		JLabel label = new JLabel("考勤机当前状态：");
		label.setBounds(170, 20, 250, 15);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("工作模式：");
		label_1.setBounds(440, 20, 250, 15);
		getContentPane().add(label_1);
		
		JLabel time = new JLabel(); 
		time.setBounds(50, 300, 300, 18);
		time.setFont(new Font("Dialog",1,15));
        add(time);  
        this.setTimer(time);
        
        JLabel week_kqj = new JLabel();
        week_kqj.setBounds(280, 300, 100, 18);
        week_kqj.setFont(new Font("Dialog",1,15));
        week_kqj.setText(getWeekOfDate(new Date()));
        getContentPane().add(week_kqj);
        init();
		setVisible(true);
	}
	
    private void setTimer(JLabel time){   
        final JLabel varTime = time;   
        Timer timeAction = new Timer(1000, new ActionListener() {          
  
            public void actionPerformed(ActionEvent e) {       
                long timemillis = System.currentTimeMillis();   
                //转换日期显示格式   
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
                String s = new String("当前时间："+df.format(new Date(timemillis)));
                varTime.setText(s);   
            }      
        });            
        timeAction.start();        
    } 
    public String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    
	public void init(){
		
		
		notice = new JLabel();
		notice.setBounds(355, 220, 270, 30);
		notice.setFont(new Font("Dialog",Font.BOLD,20));
		notice.setForeground(Color.white);
		notice.setText("欢迎您.");
		getContentPane().add(notice);
		
		stu_pic =new JLabel();
		stu_pic.setBounds(55, 50, width, height);
		
		String url = "src\\1.jpg";
		ImageIcon icon = reduceImg(url);
		stu_pic.setIcon(icon);
		getContentPane().add(stu_pic);
		
		stu_name = new JLabel("学生姓名");
		stu_name.setFont(new Font("Dialog",Font.BOLD,j_size));
		stu_name.setBounds(355, 80, 200, 50);
		getContentPane().add(stu_name);
		
		stu_num = new JLabel("学生学号");
		stu_num.setFont(new Font("Dialog",1,20));
		stu_num.setBounds(355, 125, 200, 50);
		getContentPane().add(stu_num);
		
		status_kq = new JLabel(stutas[KQCLOSE]);
		status_kq.setBounds(405, 185, 200, 25);
		status_kq.setFont(new Font("Dialog",1,20));
//		status_kq.setForeground(Color.red);
		getContentPane().add(status_kq);
		
		status_kqj = new JLabel(stutas[KQCLOSE]);
		status_kqj.setBounds(280, 20, 150, 15);
		getContentPane().add(status_kqj);
		
		status_mode = new JLabel("default");
		status_mode.setBounds(505, 20, 150, 15);
		getContentPane().add(status_mode);
		
		
	}
	public void setMode(String mode){
		status_mode.setText(mode);
	}
	
	private ImageIcon reduceImg(String url){
		String myurl;
		if(url != null){
			myurl = url;
		}else{
			myurl = default_url;
		}
		ImageIcon icon = new ImageIcon(myurl);
		icon.setImage(icon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		return icon;
	}
	
	public void setstu_data(String urlpic, String name, String id){
		ImageIcon icon = reduceImg(urlpic);
		if(icon != null){
			stu_pic.setIcon(icon);
		}
		stu_name.setText(name);
		stu_num.setText(id);
	}
	
	public void set_status_kq(String kq_status){
		status_kq.setText(kq_status);
	}
	
	public void set_status_kqj(String kqj_status){
		status_kqj.setText(kqj_status);
	}
	
	public void set_notice(String notices){
		notice.setText(notices);
	}
	
	//更新UI
	public void check_Time(){
		set_status_kq("考勤时间。");
		set_status_kqj("考勤时间。");
	}
	
	public void resetInfo(){
		stu_name.setText("学生姓名");
		stu_num.setText("学生学号");
		
		ImageIcon icon = reduceImg(default_url);
		stu_pic.setIcon(icon);
	}
	public void nocheck_Time(){

		set_status_kq("非考勤时间");
		set_status_kqj("非考勤时间。");
	}
	public static void main(String[] args) throws IOException {
		UItest test = new UItest();

	}
}


