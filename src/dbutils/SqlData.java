package dbutils;

import java.sql.SQLException;

public class SqlData {
	public String fields;
	public String values;
	
	String[] fieldArr;
	String[] valueArr;
	
	public int length = 0;
	public SqlData(String fields, String values) 
			throws SQLException {
		this.fields = fields;
		this.values = values;
		fieldArr = fields.split( "," );
		valueArr = values.split( "," );
		this.length = fieldArr.length;
		if( fieldArr.length != valueArr.length ){
			throw new SQLException();
		}
	}
	
	public String getField( int index ){
		index -= 1;
		if( index < 0 || index >= fieldArr.length )
			return "";
		return fieldArr[ index ];
	}
	
	public String getValue( int index ){
		index -= 1;
		if( index < 0 || index >= valueArr.length )
			return "";
		return valueArr[ index ];
	}
	
}
