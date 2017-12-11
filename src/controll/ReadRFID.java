package controll;

import com.gec.rfid.RfidModule;
import com.gec.rfid.SerialCallBack;
import com.gec.rfid.SerialDevice;

public class ReadRFID implements SerialCallBack {

	SerialDevice rsDevice;
	RfidModule module;
	byte[] mybuff;
	
	public boolean init(){
		//[1] ����һ������ͨ�Ŷ���
		rsDevice = new SerialDevice();
		
		//[2] ���ô������ݻص��ӿ�
		rsDevice.setCallBack( this );
		
		//[3] ������ǰϵͳ�����ӵĴ����豸
		String devName = rsDevice.findSerial();
		
		//[4] ��ڴ����豸
		rsDevice.openSerialDevice( devName );
		
		//[5] ���� RFID ��������
		module = new RfidModule();
		
		//[6] ��ģ��ӵ� "����" �ϡ�
		module.setSerialDevice( rsDevice );
		
		try {
			Thread.sleep( 1000 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//[7] ����Ƿ���� ?
		module.showRFIDInfo();
		
		System.out.println("ˢ��ģ�����");
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
				System.out.println("����ˢ���ɹ���");
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
			System.out.println("��ȡ�����š�");
			
			for(int i = 7;i>3; i--){
				String content = String.format( "%02X", mybuff[i] );
				sb.append( content );
			}
			System.out.println("RFID:0X"+sb);
			return sb.toString();
			
		}else{
			System.out.println("û�л�ȡ�����š�");
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
	 * prtArray()��������Ҫ�Ķ��ģ��Ķ��İ�buff��ʽ��Ϊstring���͡���format������
	 */
	public void onReceiveData(byte[] buff) {
		mybuff = buff;
//		ByteUtils.prtArray(buff,"[Main] prt: ");
	}
}
