package dbutils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Utils {

	//数据源对象
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
		//只需要实例化它则可
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
			dataSource.setIdleConnectionTestPeriod( 60 );    //每 60 秒检查一次是否有空闲的连接			
			dataSource.setAcquireIncrement( 3 );
			dataSource.setBreakAfterAcquireFailure( true );
			dataSource.setTestConnectionOnCheckin( false );
			
			//c3p0.maxIdleTimeExcessConnections=20
			//c3p0.maxConnectionAge=20 

/*
	<!--连接池中保留的最小连接数。-->  
	<property name="minPoolSize" value="10" />
	
	<!--连接池中保留的最大连接数。Default: 15 -->  
	<property name="maxPoolSize" value="100" />
	
	<!-- 最大空闲时间, 1800 秒内未使用则连接被丢弃。若为 0 则永不丢弃。Default: 0 -->  
	<property name="maxIdleTime" value="1800" />
	
	<!--当连接池中的连接耗尽的时候 c3p0 一次同时获取的连接数。Default: 3 -->  
	<property name="acquireIncrement" value="3" />  
	<property name="maxStatements" value="1000" />  
	<property name="initialPoolSize" value="10" />
	 
	<!--每 60 秒检查所有连接池中的空闲连接。Default: 0 -->  
	<property name="idleConnectionTestPeriod" value="60" /> 
	 
	<!--定义在从数据库获取新连接失败后重复尝试的次数。Default: 30 -->  
	<property name="acquireRetryAttempts" value="30" />  
	<property name="breakAfterAcquireFailure" value="true" />  
	<property name="testConnectionOnCheckout" value="false" />
*/
			
		}
		catch (PropertyVetoException e)
		{
			System.out.println("数据源加载失败");
		}
	}
	
}
