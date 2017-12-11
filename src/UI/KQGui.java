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
	 * һ�����⣺
	һ���������������ĳ��ͼƬ��Ϊͼ�꣬��Ϊ��label(Ӧ��˵�����)�Ѿ��趨�˹̶���С��
	�����ٴ�һЩ��ͼƬʱ�����������С�Ĳ���û��ʾ��������СͼƬ��û������������
	
	�������������⣬��Ҫ�õ������ࣺ
	java.awt.Image��
	javax.swing.ImageIcon��
	
	
	1.java.awt.Image�Ǹ������࣬����������õ��Ĳ����ͺ������£�
	��1��static int SCALE_DEFAULT    ��ʾĬ�ϵ�ͼ�������㷨��
	��2��public Image getScaledInstance(int width,int height,int hints)
	������ͼ������Ű汾������һ���µ� Image ����Ĭ������£��ö���ָ���� width �� height ����ͼ�񡣼�ʹ�Ѿ���ȫ�����˳�ʼԴͼ���µ� Image ����Ҳ���Ա��첽���ء�
	��� width �� height Ϊ���������滻��ֵ��ά�ֳ�ʼͼ��ߴ�ĸ߿�ȡ���� width �� height ��Ϊ������ʹ�ó�ʼͼ��ߴ硣
	
	������
	width - ��ͼ�����ŵ��Ŀ�ȡ�
	height - ��ͼ�����ŵ��ĸ߶ȡ�
	hints - ָʾ����ͼ������ȡ�����㷨���͵ı�־��
	���أ�
	ͼ������Ű汾��
	
	
	2.javax.swing.ImageIcon��
	��1������õ�������캯����
	ImageIcon(String filename)        ����ָ�����ļ�����һ�� ImageIcon����
	��2��Image getImage()             ���ش�ͼ��� Image��
	��3��void setImage(Image image)   �����ɴ�ͼ����ʾ��ͼ��
	
	JLabel jlb = new JLabel();  //ʵ����JLble  
	int width = 50,height = 50; //����ͼƬ��JLable�Ŀ�Ⱥ͸߶�  
	ImageIcon image = new ImageIcon("image/img1.jpg");//ʵ����ImageIcon ����  
	���������˼�ǣ��õ���ͼ��� Image��image.getImage()���� 
	�ڴ˻����ϴ����������Ű汾�����Ű汾�Ŀ�ȣ��߶���JLbleһ�£�getScaledInstance(width, height,Image.SCALE_DEFAULT )�� 
	����ͼ�������Ϊ�õ������Ű汾��image.setImage�� 
	
	image.setImage(image.getImage().getScaledInstance(width, height,Image.SCALE_DEFAULT ));//�����������������������  
	//Image img = image.getImage();  
	//img = img.getScaledInstance(width, height, Image.SCALE_DEFAULT);  
	//image.setImage(img);  
	jlb.setIcon(image);  
	jlb.setSize(width, height);  
	 */


/*
 * �����ַ��������ϡ�����ʱ�䡢�ǿ���ʱ�䡢���ڳɹ�������ʧ�ܡ�
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
		setTitle("���ڻ�");
		
		JLabel lblNewLabel = new JLabel("������");
		lblNewLabel.setFont(new Font("Dialog",1,20));
		lblNewLabel.setBounds(300, 110, 100, 50);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ѧ�ţ�");
		lblNewLabel_1.setFont(new Font("Dialog",1,20));
		lblNewLabel_1.setBounds(300, 155, 100, 50);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("����״̬��");
		lblNewLabel_2.setFont(new Font("Dialog",1,20));
		lblNewLabel_2.setBounds(300, 215, 200, 25);
		getContentPane().add(lblNewLabel_2);
		
		JLabel label_icon = new JLabel("��ѧ����");
		ImageIcon ii = reduceImg("src\\haida.png", 60, 60);
		label_icon.setIcon(ii);
		label_icon.setBounds(40, 10, 60, 60);
		getContentPane().add(label_icon);
		
		JLabel label_i = new JLabel("��ѧ����");
		ImageIcon i = reduceImg("src\\p.png", 230, 66);
		label_i.setIcon(i);
		label_i.setBounds(110, 10, 231, 67);
		getContentPane().add(label_i);
		
		JLabel label = new JLabel("���ڻ���ǰ״̬��");
		label.setBounds(400, 42, 250, 15);
		getContentPane().add(label);
		
		JLabel label_1 = new JLabel("����ģʽ��");
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
                //ת��������ʾ��ʽ   
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
                String s = new String("��ǰʱ�䣺"+df.format(new Date(timemillis)));
                varTime.setText(s);   
            }      
        });            
        timeAction.start();        
    } 
    private String getWeekOfDate(Date dt) {
        String[] weekDays = {"������", "����һ", "���ڶ�", "������", "������", "������", "������"};
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
		notice.setText("��ӭ��.");
		getContentPane().add(notice);
		
		stu_pic =new JLabel();
		stu_pic.setBounds(90, 80, width, height);
		
		String url = "src\\1.jpg";
		ImageIcon icon = reduceImg(url,width, height);
		stu_pic.setIcon(icon);
		getContentPane().add(stu_pic);
		
		stu_name = new JLabel("ѧ������");
		stu_name.setFont(new Font("Dialog",Font.BOLD,j_size));
		stu_name.setBounds(355, 110, 200, 50);
		getContentPane().add(stu_name);
		
		stu_num = new JLabel("ѧ��ѧ��");
		stu_num.setFont(new Font("Dialog",1,20));
		stu_num.setBounds(355, 155, 200, 50);
		getContentPane().add(stu_num);
		
		status_kq = new JLabel("�ǿ���ʱ��!");
		status_kq.setBounds(405, 215, 200, 25);
		status_kq.setFont(new Font("Dialog",1,20));
		status_kq.setForeground(Color.red);
		getContentPane().add(status_kq);
		
		status_kqj = new JLabel("�ǿ���ʱ��!");
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
	
	//����UI
	public void check_Time(){
		set_status_kq("����ʱ�䡣");
		set_status_kqj("����ʱ�䡣");
	}
	
	public void resetInfo(){
		stu_name.setText("ѧ������");
		stu_num.setText("ѧ��ѧ��");
		
		ImageIcon icon = reduceImg(default_url, width, height);
		stu_pic.setIcon(icon);
	}
	public void nocheck_Time(){

		set_status_kq("�ǿ���ʱ��");
		set_status_kqj("�ǿ���ʱ�䡣");
	}
	public static void main(String[] args) throws IOException {
		KQGui test = new KQGui();

	}
}

