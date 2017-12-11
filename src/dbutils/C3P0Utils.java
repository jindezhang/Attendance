package dbutils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Utils {

	//����Դ����
	private static ComboPooledDataSource dataSource = null;
	
	public static Connection getConnection(){
		if( dataSource==null )
			return null;
		Connection conn = null;
		try {
			conn = dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void destroyDataSource() {
		if( dataSource==null )
			return;
		Trace.print();
		dataSource.close();
	}
	
	static {
		//ֻ��Ҫʵ���������
		dataSource = new ComboPooledDataSource();
		try {
			
			dataSource.setDriverClass( "com.mysql.jdbc.Driver" );
			dataSource.setUser( "root" );
			dataSource.setPassword("123456");
			dataSource.setJdbcUrl( "jdbc:mysql://localhost:3306/student?"+
			"useUnicode=true&characterEncoding=utf8");
			
			dataSource.setInitialPoolSize( 10 );
			dataSource.setMaxPoolSize( 60 );
			dataSource.setMaxStatements( 30 );
			dataSource.setMaxIdleTime( 60*15 );
			dataSource.setMaxConnectionAge( 25 );
			dataSource.setUnreturnedConnectionTimeout( 25 );
			
			//c3p0.unreturnedConnectionTimeout=25
			
			dataSource.setCheckoutTimeout( 1200 );			
			dataSource.setIdleConnectionTestPeriod( 60 );    //ÿ 60 ����һ���Ƿ��п��е�����			
			dataSource.setAcquireIncrement( 3 );
			dataSource.setBreakAfterAcquireFailure( true );
			dataSource.setTestConnectionOnCheckin( false );
			
			//c3p0.maxIdleTimeExcessConnections=20
			//c3p0.maxConnectionAge=20 

/*
	<!--���ӳ��б�������С��������-->  
	<property name="minPoolSize" value="10" />
	
	<!--���ӳ��б����������������Default: 15 -->  
	<property name="maxPoolSize" value="100" />
	
	<!-- ������ʱ��, 1800 ����δʹ�������ӱ���������Ϊ 0 ������������Default: 0 -->  
	<property name="maxIdleTime" value="1800" />
	
	<!--�����ӳ��е����Ӻľ���ʱ�� c3p0 һ��ͬʱ��ȡ����������Default: 3 -->  
	<property name="acquireIncrement" value="3" />  
	<property name="maxStatements" value="1000" />  
	<property name="initialPoolSize" value="10" />
	 
	<!--ÿ 60 �����������ӳ��еĿ������ӡ�Default: 0 -->  
	<property name="idleConnectionTestPeriod" value="60" /> 
	 
	<!--�����ڴ����ݿ��ȡ������ʧ�ܺ��ظ����ԵĴ�����Default: 30 -->  
	<property name="acquireRetryAttempts" value="30" />  
	<property name="breakAfterAcquireFailure" value="true" />  
	<property name="testConnectionOnCheckout" value="false" />
*/
			
		}
		catch (PropertyVetoException e)
		{
			System.out.println("����Դ����ʧ��");
		}
	}
	
}
