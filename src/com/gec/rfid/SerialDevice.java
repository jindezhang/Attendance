package com.gec.rfid;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.gec.exception.ListenerException;
import com.gec.exception.NotSerialPortException;
import com.gec.exception.OutputCloseException;
import com.gec.exception.ParameterException;
import com.gec.exception.WriteException;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class SerialDevice implements SerialPortEventListener {  
    
    static Set<CommPortIdentifier> portList = new HashSet<CommPortIdentifier>();  
    private SerialPort serialport;
    
    byte[] readBuff = new byte[100];
    SerialCallBack callBack;
    public void setCallBack(SerialCallBack _callBack){
    	this.callBack = _callBack;
    }
    
    /*
                     ���ܽ���: ���Ҵ����豸, ����ҵ������  rsList��
                     ��������: findSerial
                    ���� String: com7
     */
    public String findSerial() {
    	List<String> rsList = null;
    	rsList = SerialManager.findPort();
        //PS: ����Ƿ��п��ô���, ������� --> List<String>��
        if (rsList!=null && rsList.size()!=0) {
        	System.out.printf( "[SerialDevice] �ɹ������������豸: [%s]\n", rsList.get(0) );
            return rsList.get(0);
        }
        prt( "û����������Ч�����豸!" );
        return null;
    }

    /*
     	���ܽ���: 
     	  [1] �򿪴����豸��
     	  [2] ����������¼���
     	  [3] һ�����ݷ�Ӧ�����ص������ "serialEvent" ������
     	��������: openSerialDevice
     	���봮���豸��: �� com5
     	Ĭ�ϲ�����: 9600 Hz
    */
    public void openSerialDevice(String devName){
        //[2] ���ò�����: 9600 HZ
        int baudrate = 9600;
        // ��鴮�������Ƿ��ȡ��ȷ
        if (devName==null || devName.equals("")) {
            prt("û����������Ч�����豸!");
        } else {
            try {
				serialport = SerialManager.openPort(devName, baudrate);
			} catch (ParameterException | NotSerialPortException
					| NoSuchPortException | PortInUseException e) {
				e.printStackTrace();
			}
            if (serialport != null) {
               prt("�����豸�Ѵ�!");
            }
        }
        try {
    	    SerialManager.addListener(serialport, this);
    	    prt("�򿪴����¼�����..");
		} catch (ListenerException e) {
			e.printStackTrace();
		}
    }

	//[2] �رմ��� [closeSerialPort]
	public void closeSerialPort(){
		SerialManager.closePort(serialport);
		prt("�����豸�ѹر�!");
	}
	
	//[2] д���ݵ����� [sendData]
	public void sendData(String data){
        System.out.println("[SerialDevice] sendData:"+ data);
        if( serialport==null ){
        	prt( "�����豸�����ڡ�" );
        	return;
        }
        try {
			SerialManager.sendToPort(
				  serialport, ByteUtils.hexStrToByte( data ) );
		} catch (WriteException | OutputCloseException e) {
			e.printStackTrace();
		} finally{
			
		}
	}
	
	public void sendData(byte[] buff){
        if( serialport==null ){
        	prt( "�����豸�����ڡ�" );
        	return;
        }
        try {
			SerialManager.sendToPort( serialport, buff );
		} catch (WriteException | OutputCloseException e) {
			e.printStackTrace();
		} finally{
			
		}
	}

    private void prt(String str) {
		System.out.println( "[SerialDevice] "+ str );
	}

    /*  -------------------------  �����¼�������  ----------------------------- */
	@Override
	public void serialEvent(SerialPortEvent event) {
//		prt( "(238) ���������¼�.." );
        switch ( event.getEventType() ) {
            case SerialPortEvent.BI:  // 10 ͨѶ�ж�
                prt( "�봮���豸ͨѶ�ж�" );
            break;
            case SerialPortEvent.OE:  // [7] ��λ(���)����
            case SerialPortEvent.FE:  // [9] ֡����
            case SerialPortEvent.PE:  // [8] ��żУ�����
            case SerialPortEvent.CD:  // [6] �ز����
            case SerialPortEvent.CTS: // [3] �������������
            case SerialPortEvent.DSR: // [4] ����������׼������
            case SerialPortEvent.RI:  // [5] ����ָʾ
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2 ��������������
            break;
            case SerialPortEvent.DATA_AVAILABLE: // 1 ���ڴ��ڿ�������
                byte[] data = null;
                try {
                    if (serialport==null) {
                        prt("���ڶ���Ϊ��, ����ʧ��!");
                    } else {
                        data = SerialManager.readFromPort(serialport);
                        if( callBack!=null ){
                        	 callBack.onReceiveData( data );
                        }
                    }
                } catch (Exception e) {
                    prt( e.toString() );
                    System.exit(0);
                }
            break;
        }
	}

}
