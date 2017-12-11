package controll;

import com.gec.rfid.RfidModule;
import com.gec.rfid.SerialCallBack;
import com.gec.rfid.SerialDevice;

public class ReadRFID implements SerialCallBack {

	SerialDevice rsDevice;
	RfidModule module;
	byte[] mybuff;
	
	public boolean init(){
		//[1] 创建一个串口通信对象
		rsDevice = new SerialDevice();
		
		//[2] 设置串口数据回调接口
		rsDevice.setCallBack( this );
		
		//[3] 搜索当前系统已连接的串行设备
		String devName = rsDevice.findSerial();
		
		//[4] 打口串口设备
		rsDevice.openSerialDevice( devName );
		
		//[5] 创建 RFID 读卡对象
		module = new RfidModule();
		
		//[6] 将模块接到 "串口" 上。
		module.setSerialDevice( rsDevice );
		
		try {
			Thread.sleep( 1000 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//[7] 检测是否存在 ?
		module.showRFIDInfo();
		
		System.out.println("刷卡模块存在");
		return true;
	}

	public boolean readRequest(){
			module.piccRequest();
			
			try {
				Thread.sleep( 200 );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(mybuff[2] == 0x00){
				System.out.println("请求刷卡成功。");
				return true;
			}else{
				return false;
			}		
	}
	
	public String getRFID(){
		StringBuffer sb = new StringBuffer();
		module.piccAnticoll();
		try {
			Thread.sleep( 500 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(mybuff[2] == 0x00){
			System.out.println("获取到卡号。");
			
			for(int i = 7;i>3; i--){
				String content = String.format( "%02X", mybuff[i] );
				sb.append( content );
			}
			System.out.println("RFID:0X"+sb);
			return sb.toString();
			
		}else{
			System.out.println("没有获取到卡号。");
			return null;
		}	
	}
	
	public static void main(String[] args) {
		ReadRFID rd = new ReadRFID();
		rd.init();
		while(true){
			
			while(!rd.readRequest());
			String id = rd.getRFID();
		}
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see com.gec.rfid.SerialCallBack#onReceiveData(byte[])
	 * prtArray()方法是需要改动的，改动的把buff格式化为string类型。用format方法。
	 */
	public void onReceiveData(byte[] buff) {
		mybuff = buff;
//		ByteUtils.prtArray(buff,"[Main] prt: ");
	}
}
