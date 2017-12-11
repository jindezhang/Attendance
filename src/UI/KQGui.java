package UI;
import java.awt.Color;
import java.awt.Font;
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
import javax.swing.Timer;

	/*
	 * 一、问题：
	一个程序，组件上设置某个图片作为图标，因为的label(应该说是组件)已经设定了固定大小，
	所以再打开一些大图片时，超过组件大小的部分没显示出来，而小图片又没填充完整个组件
	
	二、解决这个问题，需要用到两个类：
	java.awt.Image类
	javax.swing.ImageIcon类
	
	
	1.java.awt.Image是个抽象类，这个过程中用到的参数和函数如下：
	（1）static int SCALE_DEFAULT    表示默认的图像缩放算法。
	（2）public Image getScaledInstance(int width,int height,int hints)
	创建此图像的缩放版本。返回一个新的 Image 对象，默认情况下，该对象按指定的 width 和 height 呈现图像。即使已经完全加载了初始源图像，新的 Image 对象也可以被异步加载。
	如果 width 或 height 为负数，则替换该值以维持初始图像尺寸的高宽比。如果 width 和 height 都为负，则使用初始图像尺寸。
	
	参数：
	width - 将图像缩放到的宽度。
	height - 将图像缩放到的高度。
	hints - 指示用于图像重新取样的算法类型的标志。
	返回：
	图像的缩放版本。
	
	
	2.javax.swing.ImageIcon类
	（1）这儿用到这个构造函数：
	ImageIcon(String filename)        根据指定的文件创建一个 ImageIcon对象
	（2）Image getImage()             返回此图标的 Image。
	（3）void setImage(Image image)   设置由此图标显示的图像。
	
	JLabel jlb = new JLabel();  //实例化JLble  
	int width = 50,height = 50; //这是图片和JLable的宽度和高度  
	ImageIcon image = new ImageIcon("image/img1.jpg");//实例化ImageIcon 对象  
	下面这句意思是：得到此图标的 Image（image.getImage()）； 
	在此基础上创建它的缩放版本，缩放版本的宽度，高度与JLble一致（getScaledInstance(width, height,Image.SCALE_DEFAULT )） 
	最后该图像就设置为得到的缩放版本（image.setImage） 
	
	image.setImage(image.getImage().getScaledInstance(width, height,Image.SCALE_DEFAULT ));//可以用下面三句代码来代替  
	//Image img = image.getImage();  
	//img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);  
	//image.setImage(img);  
	jlb.setIcon(image);  
	jlb.setSize(width, height);  
	 */


/*
 * 常用字符串：故障、考勤时间、非考勤时间、考勤成功、考勤失败。
 */
public class KQGui extends JFrame {
	
	private JLabel stu_pic;
	private JLabel stu_name;
	private JLabel stu_num;
	private JLabel status_kq;
	private JLabel status_kqj;
	private JLabel notice;
	private JLabel status_mode;
	
	private int width;
	private int height;
	private int j_size = 20;
	private String default_url = "src\\1.jpg";
	
	
	public KQGui() {
		// TODO Auto-generated constructor stub
		
		width = 160;
		height = 190;
		setSize(620,370);
		Color c = new Color(59, 122, 202);
		getContentPane().setBackground(c);
		getContentPane().setLayout(null);
		setTitle("考勤机");
		
		JLabel lblNewLabel = new JLabel("姓名：");
		lblNewLabel.setFont(new Font("Dialog",1,20));
		lblNewLabel.setBounds(300, 110, 100, 50);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("学号：");
		lblNewLabel_1.setFont(new Font("Dialog",1,20));
		lblNewLabel_1.setBounds(300, 155, 100, 50);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("考勤状态：");
		lblNewLabel_2.setFont(new Font("Dialog",1,20));
		lblNewLabel_2.setBounds(300, 215, 200, 25);
		getContentPane().add(lblNewLabel_2);
		
		JLabel label_icon = new JLabel("广学明德");
		ImageIcon ii = reduceImg("src\\haida.png", 60, 60);
		label_icon.setIcon(ii);
		label_icon.setBounds(40, 10, 60, 60);
		getContentPane().add(label_icon);
		
		JLabel label_i = new JLabel("广学明德");
		ImageIcon i = reduceImg("src\\p.png", 230, 66);
		label_i.setIcon(i);
		label_i.setBounds(110, 10, 231, 67);
		getContentPane().add(label_i);
		
		JLabel label = new JLabel("考勤机当前状态：");
		label.setBounds(400, 42, 250, 15);
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
    private String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
    
    private void init(){
		notice = new JLabel();
		notice.setBounds(355, 250, 270, 30);
		notice.setFont(new Font("Dialog",Font.BOLD,20));
		notice.setForeground(Color.white);
		notice.setText("欢迎您.");
		getContentPane().add(notice);
		
		stu_pic =new JLabel();
		stu_pic.setBounds(90, 80, width, height);
		
		String url = "src\\1.jpg";
		ImageIcon icon = reduceImg(url,width, height);
		stu_pic.setIcon(icon);
		getContentPane().add(stu_pic);
		
		stu_name = new JLabel("学生姓名");
		stu_name.setFont(new Font("Dialog",Font.BOLD,j_size));
		stu_name.setBounds(355, 110, 200, 50);
		getContentPane().add(stu_name);
		
		stu_num = new JLabel("学生学号");
		stu_num.setFont(new Font("Dialog",1,20));
		stu_num.setBounds(355, 155, 200, 50);
		getContentPane().add(stu_num);
		
		status_kq = new JLabel("非考勤时间!");
		status_kq.setBounds(405, 215, 200, 25);
		status_kq.setFont(new Font("Dialog",1,20));
		status_kq.setForeground(Color.red);
		getContentPane().add(status_kq);
		
		status_kqj = new JLabel("非考勤时间!");
		status_kqj.setBounds(503, 42, 150, 15);
		getContentPane().add(status_kqj);
		
		status_mode = new JLabel("default");
		status_mode.setBounds(505, 20, 150, 15);
		getContentPane().add(status_mode);
		
		
	}
	
	private ImageIcon reduceImg(String url, int w, int h){
		String myurl;
		if(url != null){
			myurl = url;
		}else{
			myurl = default_url;
		}
		ImageIcon icon = new ImageIcon(myurl);
		icon.setImage(icon.getImage().getScaledInstance(w, h, Image.SCALE_DEFAULT));
		return icon;
	}
	
	public void setMode(String mode){
		status_mode.setText(mode);
	}
	
	public void setstu_data(String urlpic, String name, String id){
		ImageIcon icon = reduceImg(urlpic, width, height);
		stu_pic.setIcon(icon);
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
		
		ImageIcon icon = reduceImg(default_url, width, height);
		stu_pic.setIcon(icon);
	}
	public void nocheck_Time(){

		set_status_kq("非考勤时间");
		set_status_kqj("非考勤时间。");
	}
	public static void main(String[] args) throws IOException {
		KQGui test = new KQGui();

	}
}

