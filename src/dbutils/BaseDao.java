package dbutils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

/*
	BaseDao JDBC ��װ��
	����: ALECTER	
	��ǰ�汾: V1.10
	�޸�����: 2017-12-03
	��ϸ�޸ļ�(�汾�޸���־): Modify_BaseDao.txt
*/
public class BaseDao<T> {
	//��һ����Ա�������� ����Ϊ T �� class ����
	Class<T> clazz = null;
	public BaseDao(){
		clazz = getTClass();
		Trace.print( ""+ clazz );
	}
	
	public Connection getConnection(){
		return C3P0Utils.getConnection();
	}
	
	protected void closeConnection(Connection conn) throws SQLException {
		if(conn!=null){ conn.close(); }
	}
	
	@SuppressWarnings({ "unchecked", "hiding" })
	//get(conn,)
	protected <T> T get(Connection conn, String sql, Object... args ) 
			throws SQLException {
		Trace.print( " clazz: "+ clazz );
		T instance = null;
		PreparedStatement pstmt = null;
		try {
			if( conn != null ){
				pstmt = conn.prepareStatement( sql );
				setParameters( pstmt, 0, args );
				pstmt.executeQuery();                   // ִ�в�ѯ				
				instance = (T) fillProperties( pstmt );
			}
		}catch( IllegalAccessException e ){
			throw new SQLException( e.getMessage() );
		}finally{
			pstmt.close();
		}
		return instance;
	}
	

	private T fillProperties( PreparedStatement psmt )
			throws SQLException, IllegalAccessException {
		ResultSet rs = psmt.getResultSet();       //��ȡ����� ResultSet
		ResultSetMetaData md = rs.getMetaData();
		T bean = null;
		if( rs.next() ){
			try {
				bean = (T)clazz.newInstance();
				for( int index=1; index<=md.getColumnCount(); index++ ){
					/* --- ����ʱ�뼤�� ---  */
//					prtColumn( md.getColumnLabel(index),   
//							rs.getString(index) );
					BeanUtils.setProperty( bean, md.getColumnLabel(index), 
							rs.getString(index) );
				}
			} catch ( InstantiationException | InvocationTargetException
					| IllegalAccessException e ) {
				throw new IllegalAccessException();
			}
		}
		rs.close();
		return bean;
	}
	
	private void prtColumn(String col, String value){
		Trace.print( "col:"+ col +", value:"+ value );
	}
	
	
	protected int addNew(Connection conn, SqlData sql, T entity ) 
			throws SQLException {
		String table = entity.getClass().toString();
		//[] table = class userdao.Monkeys
		table = table.replaceAll( 
				"\\w+ (\\.*(\\w+))+", "$2" );
		String sqlStr = String.format( "insert into %s(%s) values(%s)",
				table, sql.fields, sql.values );
		Trace.print( sqlStr );
		int updateCount = 0;
		if( conn != null ){
			PreparedStatement psmt = conn.prepareStatement( sqlStr );
			setParameters( psmt, sql, entity );
			updateCount = psmt.executeUpdate();
			psmt.close();
		}
		return updateCount;
	}
	
	protected int batchUpdate(Connection conn, String sqlStr, List<Object[]> list )
			throws SQLException {
		int updateCount = 0;
		if( conn != null ){
			PreparedStatement psmt = conn.prepareStatement( sqlStr );
			for( Object[] objs : list ){
				setParameters( psmt, 0, objs );
				psmt.addBatch();
			}
			int[] cnts = psmt.executeBatch();
			for(int cnt : cnts){
				updateCount += cnt;
			}
			psmt.close();
		}
		return updateCount;
	}
	
	protected void addToBatch(PreparedStatement psmt, Object[] args )
			throws SQLException {
		setParameters( psmt, 0, args );
		psmt.addBatch();
	}
	
	protected void setInt(PreparedStatement psmt, int index, String str)
			throws SQLException {
		psmt.setInt( index, Integer.parseInt(str) );
	}
	protected void setString(PreparedStatement psmt, int index, String str)
			throws SQLException {
		psmt.setString(index, str);
	}
	
	
	private void setParameters( PreparedStatement psmt, 
			SqlData sql, T entity ) throws SQLException {
		for( int index=1; index <= sql.length; index++ ){
			if( sql.getValue(index).equals( "?" ) ){
				String fldName = sql.getField(index);
				Data data = null;
				data = Data.getProperty( fldName, entity );
				if( data.value==null ){
					throw new SQLException( fldName +" ��ֵΪ NULL��" );
				}
				setValue( data.type, psmt, index, data.value );
			}
		}
	}

	//д��һ��ʵ�����ݵ����ݿ�
	protected int save(Connection conn, String sql, Object...args ) throws SQLException{
		//[1] ͨ�� C3P0 ��ȡ����Դ�� Connection ����
		int updateCount = 0;
		if( null != conn ){
			PreparedStatement psmt = conn.prepareStatement(sql);
			setParameters( psmt, 0, args );
			Trace.print();	    //��ӡ������Ϣ	
			updateCount = psmt.executeUpdate();     //���� Ԥ����� Stmt ������Ҳ������ݵ����ݿ�
			psmt.close();
		}
		return updateCount;
	}
	
	protected List<T> getList(Connection conn, String sql, Object...args ) 
			throws SQLException {
		Trace.print( "length: "+ args.length );
		List<T> list = null;
		if( null != conn ){
			list = new ArrayList<T>();
			PreparedStatement psmt = conn.prepareStatement( sql );
			setParameters( psmt, 0, args );
			psmt.executeQuery();
			
			ResultSet rs = psmt.getResultSet();			
			ResultSetMetaData md = rs.getMetaData();   // [ ��Ľṹ  ]
			try{
				while( rs.next() ){
					T instance = null;
					instance = createBean( clazz, rs, md );
					list.add( instance );
				}
			} catch( IllegalAccessException e) {
				throw new SQLException( e.getMessage() );
			} finally {
				rs.close();
				psmt.close();
			}
		}
		return list;
	}

	private <K> K createBean( Class<K> clazz, ResultSet rs, ResultSetMetaData md ) 
			throws IllegalAccessException, SQLException {
		K instance = null;
		try {
			instance = (K)clazz.newInstance();
		} catch (InstantiationException e) {
			Trace.print(e);
			throw new IllegalAccessException();
		}
		for( int i=1; i<=md.getColumnCount(); i++ ){
			try {
				BeanUtils.setProperty( instance, 
						md.getColumnLabel( i ), rs.getString( i ) );
			} catch ( InvocationTargetException e ) {
				Trace.print( e );
				throw new IllegalAccessException();
			}
		}
		return instance;
	}
	
	
	protected <E> List<E> getGivenTypeList(Connection conn, String sql, Class<E> EClazz, Object...args )
			throws SQLException {
		List<E> list = null;
		if( conn!=null ){
			PreparedStatement psmt = conn.prepareStatement(sql);   // Ԥ���� sql ָ��
			setParameters( psmt, 0, args );
			psmt.executeQuery();     //ȥ���ݿ��ѯ
			list = new ArrayList<E>();    //����һ�� ArrayList<E> , װ�� E �������͵�����
			ResultSet rs = psmt.getResultSet();        //��ȡ�����
			ResultSetMetaData md = rs.getMetaData();   //ȡ����Ľṹ��Ϣ, ����˵: �������, ��������ֶ���
			while( rs.next() ){   //����ÿһ����¼
				E eBean = null;
				try {
					eBean = (E)createBean( EClazz, rs, md );
					list.add( eBean );     //��ʵ��������뼯��
				} catch (IllegalAccessException e) {
					Trace.print( e );
				}
			}
		}
		return list;
	}
	
	protected List<Object[]> getObjectList(Connection conn, String sql, Object...args ) 
			throws SQLException, IllegalAccessException {
		List<Object[]> list = null;
		if( conn!=null ){
			list = new ArrayList<Object[]>();
			PreparedStatement psmt = conn.prepareStatement( sql );
			setParameters( psmt, 0, args );
			psmt.executeQuery();
			ResultSet rs = psmt.getResultSet();
			ResultSetMetaData md = rs.getMetaData();   //ȡ����Ľṹ��Ϣ
			while( rs.next() ){
				Object[] objs = new Object[ md.getColumnCount() ];
				for( int i=1; i<=md.getColumnCount(); i++ ){
					objs[ i-1 ] = rs.getObject( i );
				}
				list.add( objs );
			}
			rs.close();
			psmt.close();
		}
		return list;
	}
	
	protected int getRecCount(Connection conn, String sql, Object...args ) 
			throws SQLException {
		int count = 0;
		if( null != conn ){
			PreparedStatement psmt = conn.prepareStatement( sql );
			setParameters( psmt, 0, args );
			ResultSet rs = null;
			try {
				rs = psmt.executeQuery();
				Trace.print();
				if( rs.next() ){
					count = rs.getInt( 1 );
					Trace.print();
				}
				Trace.print();
			} catch( SQLException e ) {
				throw new SQLException();
			}
			rs.close();
			psmt.close();
		}
		return count;
	}
	
	private void setParameters( PreparedStatement psmt, int offset,
			Object...args )
			throws SQLException{
		for( int i = 0; i<args.length; i++ ){
			if( args[i] != null ){
				Class<?> cls = args[i].getClass();   //��ȡ���� Class
				int index = i + 1 + offset;
				//prtParas( index, args[i] );
				setValue( cls, psmt, index, args[i] );
			}				
		}
	}
	
	@SuppressWarnings("unused")
	private void setValue( Class<?> cls, PreparedStatement psmt,
			int index, Object obj ) throws SQLException{
		if( cls==String.class ){
			psmt.setString( index, (String)obj );   //ע��: ����ֵ �� 1 ��ʼ
		}else if( cls==Integer.class || cls==int.class ){
			psmt.setInt( index, (Integer)obj );
		}else if( cls==Long.class || cls==long.class ){
			psmt.setLong( index, (Long)obj );
		}else if( cls==Double.class || cls==double.class ){
			psmt.setDouble( index, (Double)obj );
		}
	}
	
	private void prtParas(int i, Object object) {
		Trace.print( "["+ i +"] "+ object );
	}

	@SuppressWarnings("unchecked")
	private Class<T> getTClass(){
		//getClass(): class com.gec.dao.UserDaoImpl
		//genericSuperclass: com.gec.dao.BaseDao<com.gec.entity.User>
		Type genericSuperclass = getClass().getGenericSuperclass();
		//Ŀ����Ҫ��ȡ: com.gec.entity.User
		Type[] arguments = ((ParameterizedType)genericSuperclass)
							.getActualTypeArguments();
		return (Class<T>)arguments[0];
	}

}
