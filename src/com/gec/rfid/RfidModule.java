package com.gec.rfid;

import java.text.Format;

public class RfidModule {
	
	public int mode = 0;
	
	public static final int NONE = 0x0;	
	public static final int GET_VERSION = 0x1;
	public static final int READ_CARD = 0x2;
	public static final int ANTICOLL = 0x3;
	
	private byte[] dataBfr = new byte[16];
	SerialDevice device;
	public void setSerialDevice(SerialDevice _device){
		this.device = _device;
	}
	
	/**
	 * ����  BCC У��λ
	 * n = ֡�� - 2
	 */
	private byte calBCC(byte[] buf, int n){
		int i;
		byte bcc = 0;
		for( i=0; i<n; i++ ){
			bcc ^= buf[i];
		}
		return (byte)(~bcc);
	}
	
	public void showRFIDInfo(){
		String data = "06-01-41-00-B9-03";
		byte[] buff = ByteUtils.hexStrToByte( data );
//		write( buff );
		setMode( GET_VERSION );
	}
	
	/**
	 * PiccRequest ��������߷�Χ�ڵ�  RFID ����
	 * ����  BCC У��λ
	 * n = ֡�� - 2
	 */
	public void piccRequest(){
		byte[] wBuf = new byte[7];
		wBuf[0] = 0x07;	        //[1] ֡��= 7 Byte
		wBuf[1] = (byte)0x02;	//[2] ����= 0 , ��������= 0x02
		wBuf[2] = 'A';	        //[3] ����= 'A'
		wBuf[3] = 0x01;	        //[4] ��Ϣ����= 1
		wBuf[4] = 0x52;	        //[5] ����ģʽ:  ALL=0x52
		wBuf[5] = calBCC(wBuf, wBuf[0]-2);  //[6] У���
		wBuf[6] = 0x03; 	    //[7] ������־
		write( wBuf );
		setMode( READ_CARD );
	}
	
	public void setMode(int mode) {
		this.mode = mode;
	}

	public void piccAnticoll(){
		byte[] wrBuff = new byte[8];
		wrBuff[0] = 0x08;	     //[1] ֡��= 8 Byte
		wrBuff[1] = 0x02;	     //[2] ����= 0 , ��������= 0x01
		wrBuff[2] = 0x42;	     //[3] ����= 'B'
		wrBuff[3] = 0x02;	     //[4] ��Ϣ����= 2
		wrBuff[4] = (byte)0x93;	 //[5] ����ײ 0x93  һ������ײ
		wrBuff[5] = 0x00;	     //[6] λ����0
		wrBuff[6] = calBCC(wrBuff, wrBuff[0]-2);  //[7]У���
		wrBuff[7] = 0x03; 	     //[8] ������־
		write( wrBuff );
		setMode( ANTICOLL );
	}
	

	private void write(byte[] wBuf) {
//		ByteUtils.prtArray( wBuf, "write" );
		device.sendData( wBuf );
	}

	private void prt(String str) {
		System.out.println( str);
	}

	private void prtArray(byte[] buff, String msg) {
		System.out.printf( "[RFID][%s]", msg );
		StringBuffer sb = new StringBuffer();
		
		for(byte b : buff){
			String s = String.format("%02X, ", b);
			sb.append(s);
		};
		sb.setLength(sb.length()-1);
		System.out.println(sb.toString());
	}

	public void anticollOK() {
		setMode( this.NONE );
	}
	
}
