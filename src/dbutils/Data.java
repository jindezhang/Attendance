package dbutils;

import java.lang.reflect.Field;


public class Data {
	public String name;
	public Class<?> type;
	public Object value;
	public Data(String name, Class<?> type, Object value){
		this.name = name;
		this.type = type;
		this.value = value;
	}
	public Data(Class<?> type, Object value){
		this.type = type;
		this.value = value;
	}
	
	public static <T> Data getProperty(String fldName, T entity) {
		Data data = null; 
		try {
			Field field = null;
			field = entity.getClass().getDeclaredField(fldName);
			field.setAccessible( true );
			Class<?> type = field.getType();
			Trace.print( "type = "+ type );
			Object value = field.get(entity);
			Trace.print( "value = "+ value );
			data = new Data(fldName, type, value);
		} catch (NoSuchFieldException | SecurityException e) {
			Trace.print( "�����ڸ��ֶ�: "+ fldName );
		} catch (IllegalArgumentException | IllegalAccessException e) {
			Trace.print( "�����쳣: "+ fldName );
		}
		return data;
	}
}
