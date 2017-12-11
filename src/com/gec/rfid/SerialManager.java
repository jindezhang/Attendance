package com.gec.rfid;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import com.gec.exception.InputCloseException;
import com.gec.exception.ListenerException;

//import com.gec.exception.NoSuchPortException;
//import com.gec.exception.PortInUseException;

import com.gec.exception.NotSerialPortException;
import com.gec.exception.OutputCloseException;
import com.gec.exception.ParameterException;
import com.gec.exception.ReadException;
import com.gec.exception.WriteException;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

public class SerialManager {
    /**
     * �������п��ö˿�
     * @return ���ö˿������б�
     */
    @SuppressWarnings("unchecked")
    public static final ArrayList<String> findPort() {
        // ��õ�ǰ���п��ô���
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier
                .getPortIdentifiers();
        ArrayList<String> portNameList = new ArrayList<String>();
        // �����ô�������ӵ�List�����ظ�List
        while ( portList.hasMoreElements() ) {
            String portName = portList.nextElement().getName();
            portNameList.add( portName );
        }
        return portNameList;
    }

    /**
     * �򿪴���
     * @param portName    �˿�����
     * @param baudrate    ������
     * @return            ���ڶ���
     * @throws ParameterException   ���ô��ڲ���ʧ��
     * @throws NotSerialPortException  �˿�ָ���豸���Ǵ�������
     * @throws NoSuchPortException  û�иö˿ڶ�Ӧ�Ĵ����豸
     * @throws PortInUseException   �˿��ѱ�ռ��
     */
    public static final SerialPort openPort(String portName, int baudrate)
            throws ParameterException, NotSerialPortException, 
            NoSuchPortException, PortInUseException {
        try {
            //[1] ͨ���˿���ʶ��˿�
            CommPortIdentifier portIdentifier = CommPortIdentifier
                    .getPortIdentifier(portName);
            //[2] �򿪶˿�, ���ö˿����� timeout(�򿪲����ĳ�ʱʱ��)
            CommPort commPort = portIdentifier.open(portName, 2000);
            //[3] �ж��ǲ��Ǵ���
            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                try {
                    // ���ô��ڵĲ����ʵȲ���
                    serialPort.setSerialPortParams( baudrate,
                            SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE );
                } catch (UnsupportedCommOperationException e) {
                    throw new ParameterException();
                }
                return serialPort;
            } else {
                throw new NotSerialPortException(); // ���Ǵ���
            }
        } catch (NoSuchPortException e1) {
            throw new NoSuchPortException();
        } catch (PortInUseException e2) {
            throw new PortInUseException();
        }
    }

    /**
     * closePort �رմ���
     * @param serialport  ���رյĴ��ڶ���
     */
    public static void closePort(SerialPort serialPort) {
        if (serialPort != null) {
            serialPort.close();
            serialPort = null;
        }
    }

    /**
     * sendToPort �򴮿ڷ�������
     * @param serialPort  ���ڶ���
     * @param order  ����������
     * @throws WriteException �򴮿ڷ�������ʧ��
     * @throws OutputCloseException �رմ��ڶ�������������
     */
    public static void sendToPort(SerialPort serialPort, byte[] data)
            throws WriteException, OutputCloseException {
        OutputStream out = null;
        try {
            out = serialPort.getOutputStream();
            out.write( data );
            out.flush();
        } catch (IOException e) {
            throw new WriteException();
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException e) {
                throw new OutputCloseException();
            }
        }
    }

    /**
     * readFromPort �Ӵ��ڶ�ȡ����
     * @param serialPort ��ǰ�ѽ������ӵ�SerialPort����
     * @return ��ȡ��������  ---> byte[]
     * @throws ReadException �Ӵ��ڶ�ȡ����ʱ����
     * @throws InputCloseException �رմ��ڶ�������������
     */
    public static byte[] readFromPort(SerialPort serialPort)
            throws ReadException, InputCloseException {
        InputStream in = null;
        byte[] bytes = null;
        try {
            in = serialPort.getInputStream();
            // ��ȡbuffer������ݳ���
            int bufflenth = in.available();
            while (bufflenth != 0) {
                // ��ʼ��byte����Ϊbuffer�����ݵĳ���
                bytes = new byte[bufflenth];
                in.read(bytes);
                bufflenth = in.available();
            }
        } catch (IOException e) {
            throw new ReadException();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                throw new InputCloseException();
            }
        }
        return bytes;
    }

    /**
     * addListener ��Ӽ�����
     * @param port ���ڶ���
     * @param listener ���ڼ�����
     * @throws TooManyListeners  ������������
     */
    public static void addListener(SerialPort port,
            SerialPortEventListener listener) 
            		throws ListenerException {
        try {
            //[1] ��������Ӽ�����
            port.addEventListener(listener);
            prt("���������� ---> addListener");
            //[2] ���õ������ݵ���ʱ���Ѽ��������߳�
            port.notifyOnDataAvailable(true);
            //[3] ���õ�ͨ���ж�ʱ�����ж��߳�
            port.notifyOnBreakInterrupt(true);
        } catch (TooManyListenersException e) {
            throw new ListenerException();
        }
    }
    
    public static void prt( String str ){
    	System.out.println("[SerialManager] "+ str );
    }
}
